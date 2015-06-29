package net.realmproject.dcm.event.sink;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;


/**
 * Interface for components which require only simple receipt of events through
 * a single set of filters. Subscription to a bus can thus be handled as
 * <tt>bus.subscribe(this::filter, this::receive)</tt>
 * 
 * @author NAS
 *
 */
public interface DeviceEventSink {

    List<Predicate<DeviceEvent>> getFilters();

    default void setFilters(Predicate<DeviceEvent> filter) {
        setFilters(Collections.singletonList(filter));
    }

    void setFilters(List<Predicate<DeviceEvent>> filters);

    default boolean filter(DeviceEvent event) {
        synchronized (this) {
            for (Predicate<DeviceEvent> filter : new ArrayList<>(getFilters())) {
                if (!filter.test(event)) { return false; }
            }
            return true;
        }
    }

    void receive(DeviceEvent event);

}
