package net.realmproject.dcm.event.filter;


import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;


/**
 * DeviceEvent filter which always accepts the event
 * 
 * @author NAS
 *
 */

public class AcceptFilter implements Predicate<DeviceEvent> {

    @Override
    public boolean test(DeviceEvent t) {
        return true;
    }

}
