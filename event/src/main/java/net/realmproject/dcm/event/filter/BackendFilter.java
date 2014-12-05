package net.realmproject.dcm.event.filter;


import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.messaging.DeviceMessageType;


/**
 * DeviceEvent filter to allow only events intended for a 'backend', something
 * only interested in listening to get/set events, and not the status of other
 * devices
 * 
 * @author NAS
 *
 */
public class BackendFilter implements Predicate<DeviceEvent> {

    @Override
    public boolean test(DeviceEvent e) {
        DeviceMessageType type = e.getDeviceMessageType();
        return type == DeviceMessageType.VALUE_GET || type == DeviceMessageType.VALUE_SET;
    }

}
