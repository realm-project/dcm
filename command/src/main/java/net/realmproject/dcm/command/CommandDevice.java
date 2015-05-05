/**
 * Copyright 2014 Realm Project
 * 
 * This file is part of the Device Control Module (DCM).
 * 
 * DCM is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DCM is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DCM. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package net.realmproject.dcm.command;


import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

import net.realmproject.dcm.device.Device;
import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.composite.BooleanAndFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.BackendFilter;
import net.realmproject.dcm.event.filter.deviceid.DeviceIDWhitelistFilter;
import net.realmproject.dcm.util.DCMSerialize;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Building on top of the {@link Device} component, a {@link CommandDevice}
 * accepts values of type {@link Command} and returns values of type T.<br>
 * <br>
 * Methods annotated with the {@link CommandMethod} interface will be detected
 * automatically, and commands with an action field matching the method name (or
 * CommandMethod annotation value) will automatically be routed to the
 * corresponding method.<br>
 * <br>
 * CommandMethods may accept 0 or 1 arguments. If a method accepts 1 argument,
 * the arguments section of a received command will be converted into the
 * appropriate data type via reflection.
 * 
 * @author NAS
 *
 * @param <T>
 *            The data type used to report the state of this device
 */

public abstract class CommandDevice<T extends DeviceState> extends Device {

	private final Log LOG = LogFactory.getLog(getClass());
    private Map<String, Method> commands;

    /**
     * Create a new CommandDevice with the given id which listens to and
     * publishes on the given {@link DeviceEventBus}
     * 
     * @param id
     *            the id of this device
     * @param bus
     *            the bus on which to listen and publish
     */
    protected CommandDevice(String id, DeviceEventBus bus) {
        super(id, bus);

        Predicate<DeviceEvent> eventFilter = new BooleanAndFilter(new DeviceIDWhitelistFilter(getId()),
                new BackendFilter());
        bus.subscribe(this::handleEvent, eventFilter);

        commands = getCommandMethods();
    }

    // ////////////////////////////////////////////
    // State-related methods
    // ////////////////////////////////////////////
    /**
     * Queries the state of this CommandDevice. This method should return an
     * object representing the full state of this device. While representing
     * this device's state, this value should be stateless in the sense that an
     * observer need not rely on previous values in order to properly understand
     * this one.
     * 
     * @return the current state of this device
     */
    protected abstract T getState();

    // ////////////////////////////////////////////
    // Event-related methods
    // ////////////////////////////////////////////

    private final void handleEvent(DeviceEvent deviceEvent) {

        switch (deviceEvent.getDeviceMessageType()) {
            case VALUE_GET:
                publish();
                return;

            case VALUE_SET:
                setValue(deviceEvent.getValue());
                return;

            case VALUE_CHANGED:
            default:
                return;
        }

    }

    @Override
    public final Map<String, Serializable> getValue() {
        return DCMSerialize.structToMap(getState());
    }

    @Override
    public synchronized final void setValue(Object o) {

    	Command command = DCMSerialize.convertMessage(o, Command.class);
        try {
            
            onCommand(command);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        catch (Exception e) {
            LOG.error("Error executing command " + command.action, e);
        }
    }

    // ////////////////////////////////////////////
    // Command-related methods
    // ////////////////////////////////////////////

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
    protected boolean beforeCommand(Command command) {
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
    protected void afterCommand(Command command) {}

    private final void onCommand(Command command) throws Exception {

        LOG.info("Device " + getId() + " received command: " + command.action);
        LOG.debug(getId() + ":" + command.action + ":" + DCMSerialize.serialize(command.arguments));

        // Look up method for command
        Method method = findCommandMethod(command.action);

        // case where no method
        if (method == null) {
            LOG.warn("Cannot find requested command '" + command.action + "' from available commands");
            LOG.warn("Available commands: " + commands.keySet());
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

    /*
     * Start with implementing class, work back up to anything underneath this
     * to find a CommandMethod this allows subclassing to create devices which
     * don't need to implement commands directly, but which can overload them if
     * it chooses.
     */
    private Method findCommandMethod(String name) {

        if (name == null) return null;
        if (!commands.containsKey(name)) return null;
        return commands.get(name);

    }

    private Map<String, Method> getCommandMethods() {
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

    /*
     * Given a CommandMethod, determine the name of the action it represents
     */
    private String getCommandName(Method m) {
        CommandMethod cm = m.getAnnotation(CommandMethod.class);
        if (cm == null) return null;
        if (cm.value().equals("")) return m.getName();
        return cm.value();
    }

}
