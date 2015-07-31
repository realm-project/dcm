package net.realmproject.dcm.event.filter;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;


/**
 * Interface for components which require only simple receipt of events through
 * a single set of filters. Subscription to a bus can thus be handled as
 * <tt>bus.subscribe(this::filter, ...)</tt>
 * 
 * @author NAS
 *
 */
public interface DeviceEventFilterer {

    List<Predicate<DeviceEvent>> getFilters();

    default void setFilters(Predicate<DeviceEvent> filter) {
        List<Predicate<DeviceEvent>> filters = new ArrayList<>();
        filters.add(filter);
        setFilters(filters);
    }

    void setFilters(List<Predicate<DeviceEvent>> filters);

    default boolean filter(DeviceEvent event) {
        for (Predicate<DeviceEvent> filter : new ArrayList<>(getFilters())) {
            if (!filter.test(event)) { return false; }
        }
        return true;
    }

}
