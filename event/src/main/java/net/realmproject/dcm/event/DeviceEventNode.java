package net.realmproject.dcm.event;


import java.util.function.Function;
import java.util.function.Predicate;


/**
 * Base interface for DCM event graph nodes which process events.
 * 
 * @author NAS
 *
 */

public interface DeviceEventNode {

    // Filter Predicate
    Predicate<DeviceEvent> getFilter();

    void setFilter(Predicate<DeviceEvent> filter);

    /** Method for filtering {@link DeviceEvent}s using the specified filter **/
    default boolean filter(DeviceEvent event) {
        if (!getFilter().test(event)) { return false; }
        return true;
    }

    // Transform Function
    Function<DeviceEvent, DeviceEvent> getTransform();

    void setTransform(Function<DeviceEvent, DeviceEvent> transform);

    /**
     * Method for transforming {@link DeviceEvent}s using the specified
     * transformation function
     **/
    default DeviceEvent transform(DeviceEvent event) {
        return getTransform().apply(event);
    }

}