package net.realmproject.dcm.features.command;


import java.io.Serializable;

import net.realmproject.dcm.features.accessor.DeviceAccessor;


public interface CommandsAccessor<T extends Serializable> extends DeviceAccessor<T> {

    default String sendCommand(Command command) {
        String label = null;
        if (command.isToRecord()) {
            label = command.getId();
        }
        sendMessage(command);
        return label;
    }

    default String sendCommand(String action, Serializable value) {
        return sendCommand(new Command(action).property(value));
    }

}
