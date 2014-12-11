package net.realmproject.dcm.event.filter;


import java.util.function.Predicate;

import net.realmproject.dcm.event.IDeviceEvent;


/**
 * DeviceEvent filter which always accepts the event
 * 
 * @author NAS
 *
 */

public class AcceptFilter implements Predicate<IDeviceEvent> {

    @Override
    public boolean test(IDeviceEvent t) {
        return true;
    }

}
