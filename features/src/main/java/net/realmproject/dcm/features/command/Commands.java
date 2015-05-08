package net.realmproject.dcm.features.command;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.Logging;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.composite.BooleanAndFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.BackendFilter;
import net.realmproject.dcm.event.filter.deviceid.DeviceIDWhitelistFilter;
import net.realmproject.dcm.features.Identity;
import net.realmproject.dcm.util.DCMSerialize;

public interface Commands extends Identity, Logging {

	default void initCommands(DeviceEventBus bus) {

		Predicate<DeviceEvent> eventFilter = new BooleanAndFilter(
				new DeviceIDWhitelistFilter(getId()), new BackendFilter());
		bus.subscribe(eventFilter, deviceEvent -> {
			if (deviceEvent.getDeviceEventType() == DeviceEventType.VALUE_SET) {
				Object value = deviceEvent.getValue();
				Command command = DCMSerialize.convertMessage(value, Command.class);
				runCommand(command);
			}
		});
		
		setCommandMethods(generateCommandMethods());

	}
	
	
    /**
     * This function is called before a {@link CommandMethod} is invoked. If
     * this method returns false, the method invocation will not proceed.
     * 
     * @param command
     *            The command which is about to be processed. This is before the
     *            command undergoes type conversion to match the argument type
     *            for the appropriate method.
     * 
     * @return true of the command should be executed, false otherwise
     */
    default boolean beforeCommand(Command command) {
        return true;
    }
    
    /**
     * This function is called after a {@link CommandMethod} is invoked
     * 
     * @param command
     *            The command which is about to be processed. This is before the
     *            command undergoes type conversion to match the argument type
     *            for the appropriate method.
     */
    default void afterCommand(Command command) {}
    
    


    default void runCommand(Command command) {

    	synchronized(this) {
	        try {
    	
		        getLog().info("Device " + getId() + " received command: " + command.action);
		        getLog().debug(getId() + ":" + command.action + ":" + DCMSerialize.serialize(command.arguments));
		
		        // Look up method for command
		        Method method = findCommandMethod(command.action);
		
		        // case where no method
		        if (method == null) {
		        	getLog().warn("Cannot find requested command '" + command.action + "' from available commands");
		        	getLog().warn("Available commands: " + getCommandMethods().keySet());
		            throw new IllegalArgumentException("Command not found: " + command.action);
		        }
		
		        beforeCommand(command);
		
		        int argCount = method.getParameterCount();
		
		        if (argCount == 0) {
		
		            // case where zero-arg method
		            method.invoke(this, new Object[] {});
		            return;
		
		        } else if (argCount == 1) {
		
		            // case where 1-arg method
		            method.invoke(this, DCMSerialize.convertMessage(command.arguments, method.getParameterTypes()[0]));
		
		        } else {
		
		            // case where >1-arg method
		            Object[] args = new Object[argCount];
		
		            // look up named arguments from top-level map. Then examine the
		            // parameter types, and attempt to convert the data for each
		            // argument to that type
		            for (int count = 0; count < argCount; count++) {
		                String paramName = method.getParameters()[count].getName();
		                if (!command.arguments.containsKey(paramName)) {
		                    continue;
		                }
		
		                Object arg = command.arguments.get(paramName);
		                args[count] = DCMSerialize.convertMessage(arg, method.getParameterTypes()[count]);
		
		            }
		
		            method.invoke(this, args);
		
		        }
		
		        afterCommand(command);

	        }
	        catch (Exception e) {
	        	getLog().error("Error executing command " + command.action, e);
	        }
		}
        
    }
    
    /*
     * Start with implementing class, work back up to anything underneath this
     * to find a CommandMethod this allows subclassing to create devices which
     * don't need to implement commands directly, but which can overload them if
     * it chooses.
     */
    default Method findCommandMethod(String name) {
        if (name == null) return null;
        Map<String, Method> commands = getCommandMethods();
        if (!commands.containsKey(name)) return null;
        return commands.get(name);
    }
    
    default Map<String, Method> generateCommandMethods() {
        Map<String, Method> commandMethods = new LinkedHashMap<>();
        Class<?> cls = this.getClass();

        for (Method m : cls.getMethods()) {
            if (!m.isAnnotationPresent(CommandMethod.class)) continue;
            String commandName = getCommandName(m);
            if (commandMethods.containsKey(commandName)) continue;
            commandMethods.put(commandName, m);
        }

        return commandMethods;
    }
    
    default String getCommandName(Method m) {
        CommandMethod cm = m.getAnnotation(CommandMethod.class);
        if (cm == null) return null;
        if (cm.value().equals("")) return m.getName();
        return cm.value();
    }
    
    Map<String, Method> getCommandMethods();
    void setCommandMethods(Map<String, Method> methods);


    
}
