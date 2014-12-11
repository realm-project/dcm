package net.realmproject.dcm.event.filter.composite;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import net.realmproject.dcm.event.IDeviceEvent;


public class BooleanAndFilter implements Predicate<IDeviceEvent> {

    List<Predicate<IDeviceEvent>> filters;

    @SafeVarargs
    public BooleanAndFilter(Predicate<IDeviceEvent>... filters) {
        this.filters = new ArrayList<>(Arrays.asList(filters));
    }

    @Override
    public boolean test(IDeviceEvent t) {
        for (Predicate<IDeviceEvent> filter : filters) {
            if (!filter.test(t)) { return false; }
        }
        return true;
    }

}
