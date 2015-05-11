package net.realmproject.dcm.accessor.commands;


import net.realmproject.dcm.accessor.DeviceWriter;
import net.realmproject.dcm.features.command.Command;


public interface DeviceCommander extends DeviceWriter {

    String write(Command command);

}
