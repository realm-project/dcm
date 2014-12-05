package net.realmproject.dcm.event.filter;


import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;


/**
 * DeviceEvent filter which returns the negation of a given filter
 * 
 * @author NAS
 *
 */

public class NegationFilter implements Predicate<DeviceEvent> {

    private Predicate<DeviceEvent> backer;

    public NegationFilter(Predicate<DeviceEvent> backer) {
        this.backer = backer;
    }

    @Override
    public boolean test(DeviceEvent t) {
        return !backer.test(t);
    }

}
