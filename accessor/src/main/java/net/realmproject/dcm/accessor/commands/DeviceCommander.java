package net.realmproject.dcm.accessor.commands;


import net.realmproject.dcm.accessor.DeviceAccessor;
import net.realmproject.dcm.features.command.Command;


public interface DeviceCommander extends DeviceAccessor {

    String write(Command command);

}
