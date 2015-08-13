package net.realmproject.dcm.features.command;


import java.io.Serializable;

import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.features.accessor.IDeviceAccessor;


public class ICommandsAccessor<T extends Serializable> extends IDeviceAccessor<T>implements CommandsAccessor<T> {

    public ICommandsAccessor(String id, String deviceId, DeviceEventBus bus) {
        super(id, deviceId, bus);
    }

}
