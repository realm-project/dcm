package net.realmproject.dcm.event.filter;


import java.util.function.Predicate;

import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.messaging.DeviceMessageType;


/**
 * DeviceEvent filter to allow only events intended for a 'frontend', something
 * only interested in listening to the status of devices
 * 
 * @author NAS
 *
 */
public class FrontendFilter implements Predicate<IDeviceEvent> {

    @Override
    public boolean test(IDeviceEvent e) {
        DeviceMessageType type = e.getDeviceMessageType();
        return type == DeviceMessageType.VALUE_CHANGED;
    }

}
