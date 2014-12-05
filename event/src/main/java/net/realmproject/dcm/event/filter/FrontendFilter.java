package net.realmproject.dcm.event.filter;


import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.messaging.DeviceMessageType;


/**
 * DeviceEvent filter to allow only events intended for a 'frontend', something
 * only interested in listening to the status of devices
 * 
 * @author NAS
 *
 */
public class FrontendFilter implements Predicate<DeviceEvent> {

    @Override
    public boolean test(DeviceEvent e) {
        DeviceMessageType type = e.getDeviceMessageType();
        return type == DeviceMessageType.VALUE_CHANGED;
    }

}
