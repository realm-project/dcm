package net.realmproject.dcm.event.filter.composite;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import net.realmproject.dcm.event.IDeviceEvent;


public class BooleanOrFilter implements Predicate<IDeviceEvent> {

    List<Predicate<IDeviceEvent>> filters;

    @SafeVarargs
    public BooleanOrFilter(Predicate<IDeviceEvent>... filters) {
        this.filters = new ArrayList<>(Arrays.asList(filters));
    }

    @Override
    public boolean test(IDeviceEvent t) {
        for (Predicate<IDeviceEvent> filter : filters) {
            if (filter.test(t)) { return true; }
        }
        return false;
    }

}
