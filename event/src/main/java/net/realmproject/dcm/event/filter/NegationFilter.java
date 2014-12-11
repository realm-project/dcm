package net.realmproject.dcm.event.filter;


import java.util.function.Predicate;

import net.realmproject.dcm.event.IDeviceEvent;


/**
 * DeviceEvent filter which returns the negation of a given filter
 * 
 * @author NAS
 *
 */

public class NegationFilter implements Predicate<IDeviceEvent> {

    private Predicate<IDeviceEvent> backer;

    public NegationFilter(Predicate<IDeviceEvent> backer) {
        this.backer = backer;
    }

    @Override
    public boolean test(IDeviceEvent t) {
        return !backer.test(t);
    }

}
