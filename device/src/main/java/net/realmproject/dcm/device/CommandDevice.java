package net.realmproject.dcm.device;


import java.lang.reflect.Method;
import java.util.Map;

import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.features.Statefulness;
import net.realmproject.dcm.features.Statefulness.State;
import net.realmproject.dcm.features.command.Command;
import net.realmproject.dcm.features.command.Commands;


/**
 * Implementation of Statefulness and Commands features.
 * 
 * @author NAS
 *
 * @param <T>
 *            type of device state
 */
public abstract class CommandDevice<T extends State> extends Device implements Commands, Statefulness<T> {

    Command lastCommand;

    Map<String, Method> commands;

    public CommandDevice(String id, DeviceEventBus bus) {
        super(id, bus);
        initCommands(bus);
    }

    @Override
    public Map<String, Method> getCommandMethods() {
        return commands;
    }

    @Override
    public void setCommandMethods(Map<String, Method> methods) {
        commands = methods;
    }

    @Override
    public Command getLastCommand() {
        return lastCommand;
    }

    @Override
    public void setLastCommand(Command command) {
        lastCommand = command;
    }

}
