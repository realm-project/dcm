package net.realmproject.dcm.features.command;


import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.Logging;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.Filters;
import net.realmproject.dcm.event.identity.Identity;
import net.realmproject.dcm.util.DCMSerialize;


public interface Commands extends Identity, Logging {

    default void initCommands(DeviceEventBus bus) {

        Predicate<DeviceEvent> eventFilter = Filters.targetId(getId()).and(Filters.messageEvents());
        bus.subscribe(eventFilter, deviceEvent -> {
            if (deviceEvent.getDeviceEventType() == DeviceEventType.MESSAGE) {
                Command command = (Command) deviceEvent.getPayload();
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

        synchronized (this) {
            try {

                getLog().info("Device " + getId() + " received command: " + command.getAction());
                getLog().debug(
                        getId() + ":" + command.getAction() + ":" + DCMSerialize.serialize(command.getPropertyMap()));

                // Look up method for command
                Method method = findCommandMethod(command.getAction());

                // case where no method
                if (method == null) {
                    getLog().warn(
                            "Cannot find requested command '" + command.getAction() + "' from available commands");
                    getLog().warn("Available commands: " + getCommandMethods().keySet());
                    throw new IllegalArgumentException("Command not found: " + command.getAction());
                }

                // before anything, call this hook
                boolean execute = beforeCommand(command);
                if (!execute) { return; }

                int argCount = method.getParameterCount();

                if (argCount == 0) {

                    setLastCommand(command);
                    // case where zero-arg method
                    method.invoke(this, new Object[] {});
                    return;

                } else if (argCount == 1 && method.getParameters()[0].getAnnotationsByType(Arg.class).length == 0) {
                    // if there is only 1 argument and there is no Arg
                    // annotation, try and convert the entire map to the
                    // expected parameter class type

                    setLastCommand(command);
                    method.invoke(this,
                            DCMSerialize.convertObject(command.getPropertyMap(), method.getParameterTypes()[0]));

                } else {

                    // case where >1-arg method
                    Object[] argValues = new Object[argCount];

                    // look up named arguments from top-level map. Then examine
                    // the
                    // parameter types, and attempt to convert the data for each
                    // argument to that type
                    for (int count = 0; count < argCount; count++) {
                        Parameter param = method.getParameters()[count];
                        Arg[] args = param.getAnnotationsByType(Arg.class);
                        if (args.length == 0) { throw new IllegalArgumentException(
                                "Named argument calls must have parameters with Arg annotations"); }
                        Arg arg = args[0];
                        String paramName = arg.value();
                        if (!command.getPropertyMap().containsKey(paramName)) {
                            continue;
                        }

                        Object argValue = command.getPropertyMap().get(paramName);
                        argValues[count] = DCMSerialize.convertObject(argValue, method.getParameterTypes()[count]);

                    }

                    setLastCommand(command);
                    method.invoke(this, argValues);

                }

                // after the method (results may be async), call this hook
                afterCommand(command);

            }
            catch (Exception e) {
                getLog().error("Error executing command " + command.getAction(), e);
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

    default CommandMethod getCommandMethodAnnotation(Method method) {
        CommandMethod[] annotations = method.getAnnotationsByType(CommandMethod.class);
        if (annotations.length == 0) { return null; }
        return annotations[0];
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

    Command getLastCommand();

    void setLastCommand(Command command);

}
