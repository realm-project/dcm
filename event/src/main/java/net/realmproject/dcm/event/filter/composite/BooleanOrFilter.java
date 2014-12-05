package net.realmproject.dcm.event.filter.composite;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;


public class BooleanOrFilter implements Predicate<DeviceEvent> {

    List<Predicate<DeviceEvent>> filters;

    @SafeVarargs
    public BooleanOrFilter(Predicate<DeviceEvent>... filters) {
        this.filters = new ArrayList<>(Arrays.asList(filters));
    }

    @Override
    public boolean test(DeviceEvent t) {
        for (Predicate<DeviceEvent> filter : filters) {
            if (filter.test(t)) { return true; }
        }
        return false;
    }

}
