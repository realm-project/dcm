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
public interface DeviceEventFilterer extends Predicate<DeviceEvent>{

    Predicate<DeviceEvent> getFilter();

    void setFilter(Predicate<DeviceEvent> filter);

    default boolean test(DeviceEvent event) {
        if (!getFilter().test(event)) { return false; }
         return true;
    }

}
