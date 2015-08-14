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

    /**
     * Method for filtering {@link DeviceEvent}s using the specified filter
     * 
     * @param event
     *            the event to filter against
     * 
     * @return true if the filter should pass the event, false if it should
     *         block it
     * 
     */
    default boolean filter(DeviceEvent event) {
        if (getFilter() == null) { return true; }
        if (!getFilter().test(event)) { return false; }
        return true;
    }

    // Transform Function
    Function<DeviceEvent, DeviceEvent> getTransform();

    void setTransform(Function<DeviceEvent, DeviceEvent> transform);

    /**
     * Method for transforming {@link DeviceEvent}s using the specified
     * transformation function
     * 
     * @param event
     *            the event to transform
     * 
     * @return the (optionally) modified event
     * 
     */
    default DeviceEvent transform(DeviceEvent event) {
        if (getTransform() == null) { return event; }
        return getTransform().apply(event.deepCopy());
    }

}