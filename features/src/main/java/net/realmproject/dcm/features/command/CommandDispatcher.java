package net.realmproject.dcm.features.command;


import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.FilterBuilder;
import net.realmproject.dcm.util.DCMSerialize;


public class CommandDispatcher {

    private Commands commanded;
    private Map<String, Method> methods;
    private Command latestCommand;

    public CommandDispatcher(Commands commanded, DeviceEventBus bus) {
        this.commanded = commanded;
        methods = generateCommandMethods();

        Predicate<DeviceEvent> eventFilter = FilterBuilder.start().target(commanded.getId()).eventMessage();
        bus.subscribe(eventFilter, deviceEvent -> {
            if (deviceEvent.getDeviceEventType() == DeviceEventType.MESSAGE) {
                Command command = (Command) deviceEvent.getPayload();
                submitCommand(command);
            }
        });

    }

    private Map<String, Method> generateCommandMethods() {
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

    private String getCommandName(Method m) {
        CommandMethod cm = m.getAnnotation(CommandMethod.class);
        if (cm == null) return null;
        if (cm.value().equals("")) return m.getName();
        return cm.value();
    }

    private Method findCommandMethod(String name) {
        if (name == null) return null;
        if (!methods.containsKey(name)) return null;
        return methods.get(name);
    }

    public Command getLatestCommand() {
        return latestCommand;
    }

    public void submitCommand(Command command) {

        synchronized (this) {
            try {

                commanded.getLog().info("Device " + commanded.getId() + " received command: " + command.getAction());
                commanded.getLog().debug(commanded.getId() + ":" + command.getAction() + ":"
                        + DCMSerialize.serialize(command.getPropertyMap()));

                // Look up method for command
                Method method = findCommandMethod(command.getAction());

                // case where no method
                if (method == null) {
                    commanded.getLog().warn(
                            "Cannot find requested command '" + command.getAction() + "' from available commands");
                    commanded.getLog().warn("Available commands: " + methods.keySet());
                    throw new IllegalArgumentException("Command not found: " + command.getAction());
                }

                // before anything, call this hook
                boolean execute = commanded.beforeCommand(command);
                if (!execute) { return; }

                int argCount = method.getParameterCount();

                if (argCount == 0) {

                    latestCommand = command;
                    // case where zero-arg method
                    method.invoke(this, new Object[] {});
                    return;

                } else if (argCount == 1 && method.getParameters()[0].getAnnotationsByType(Arg.class).length == 0) {
                    // if there is only 1 argument and there is no Arg
                    // annotation, try and convert the entire map to the
                    // expected parameter class type

                    latestCommand = command;
                    Object arg = DCMSerialize.convertObject(command.getPropertyMap(), method.getParameterTypes()[0]);
                    method.invoke(this, arg);

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

                    latestCommand = command;
                    method.invoke(this, argValues);

                }

                // after the method (results may be async), call this hook
                commanded.afterCommand(command);

            }
            catch (Exception e) {
                commanded.getLog().error("Error executing command " + command.getAction(), e);
            }
        }

    }

}
