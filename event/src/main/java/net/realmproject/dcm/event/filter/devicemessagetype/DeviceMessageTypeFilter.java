package net.realmproject.dcm.event.filter.devicemessagetype;


import java.util.function.Predicate;

import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.messaging.DeviceMessageType;


public class DeviceMessageTypeFilter implements Predicate<IDeviceEvent> {

    private DeviceMessageType type;

    public DeviceMessageTypeFilter(DeviceMessageType type) {
        this.type = type;
    }

    @Override
    public boolean test(IDeviceEvent t) {
        return t.getDeviceMessageType() == type;
    }

}
