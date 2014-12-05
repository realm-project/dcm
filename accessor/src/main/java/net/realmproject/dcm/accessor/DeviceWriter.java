package net.realmproject.dcm.accessor;


import net.realmproject.dcm.command.Command;


public interface DeviceWriter extends DeviceAccessor {

    String write(Command command);
}
