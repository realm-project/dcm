package net.realmproject.dcm.event.relay;

import java.util.function.Function;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;

public interface DeviceEventRelay extends Predicate<DeviceEvent>, Function<DeviceEvent, DeviceEvent> {

	
	//Filter Predicate
	Predicate<DeviceEvent> getFilter();

	void setFilter(Predicate<DeviceEvent> filter);

	/** Java Predicate test for filtering {@link DeviceEvent}s **/
    default boolean test(DeviceEvent event) {
    	if (!isSending()) { return false; }
        if (!getFilter().test(event)) { return false; }
         return true;
    }
    
    /** Java Predicate test for filtering {@link DeviceEvent}s. This is a convenience method for test() **/ 
    default boolean filter(DeviceEvent event) {
    	return test(event);
    }
    
	//Transform Function
	Function<DeviceEvent, DeviceEvent> getTransform();

	void setTransform(Function<DeviceEvent, DeviceEvent> transform);
	
	/** Java Predicate test for transforming {@link DeviceEvent}s **/
    default DeviceEvent apply(DeviceEvent event) {
    	return getTransform().apply(event);
    }
    
    /** Java Predicate test for filtering {@link DeviceEvent}s. This is a convenience method for apply() **/
    default DeviceEvent transform(DeviceEvent event) {
    	return apply(event);
    }
    
    //Node Sending/Relaying Enabled/Disabled
    /**
     * Is sending of events turned on?
     * 
     * @return true if this component is currently sending events, false
     *         otherwise
     */
    boolean isSending();
    
    /**
     * Set if this component is currently sending events. There is no backlog of
     * unsent events retained. Events "sent" while this component was not
     * sending are lost.
     * 
     * @param sending
     *            whether to send events or not
     */
    void setSending(boolean sending);
    
    /**
     * If this component is not currently sending events, start sending them
     * now. There is no backlog of unsent events retained. Events "sent" while
     * this component was not sending are lost.
     */
    default void startSending() {
    	setSending(true);
    }
    
    /**
     * If this component is currently sending events, stop sending them now.
     * There is no backlog of unsent events retained. Events "sent" while this
     * component was not sending are lost.
     */
    default void stopSending() {
    	setSending(true);
    }
    
}