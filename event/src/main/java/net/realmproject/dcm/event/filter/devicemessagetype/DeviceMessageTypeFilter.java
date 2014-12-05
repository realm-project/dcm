package net.realmproject.dcm.event.filter.devicemessagetype;


import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.messaging.DeviceMessageType;


public class DeviceMessageTypeFilter implements Predicate<DeviceEvent> {

    private DeviceMessageType type;

    public DeviceMessageTypeFilter(DeviceMessageType type) {
        this.type = type;
    }

    @Override
    public boolean test(DeviceEvent t) {
        return t.getDeviceMessageType() == type;
    }

}
