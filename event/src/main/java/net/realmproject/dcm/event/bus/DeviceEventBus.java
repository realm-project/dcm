package net.realmproject.dcm.event.bus;


import java.util.function.Consumer;
import java.util.function.Predicate;

import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.messaging.AbstractDeviceMessageEncoder;
import net.realmproject.dcm.event.messaging.DeviceMessageDecoder;
import net.realmproject.dcm.event.sender.DeviceEventSender;


/**
 * A DeviceEventBus is a component which can have {@link IDeviceEvent}s broadcast
 * from any source to the components which subscribes to it. This provides a
 * looser coupling than a traditional direct listener-based model, as consumers
 * don't need to be aware of and registered with every producer of events. <br>
 * <br>
 * It is also possible to split a larger collection of devices into regions by
 * using more than one DeviceEventBus. Events from one bus can be selectively
 * propagated to another using an {@link IDeviceEventBusForwarder} or using a
 * distributed messaging system like ActiveMQ (see
 * {@link AbstractDeviceMessageEncoder} and {@link DeviceMessageDecoder}).
 * 
 * @author NAS
 *
 */

public interface DeviceEventBus extends DeviceEventSender {

    /**
     * Send a {@link IDeviceEvent} to all subscribers of this bus. Broadcasting
     * is done asynchronously.
     * 
     * @param event
     *            The event to broadcast
     * @return true if event is accepted for broadcast, false otherwise.
     */
    boolean broadcast(IDeviceEvent event);

    /**
     * Listen for events broadcast on this event bus. All events will be sent to
     * this subscriber.
     * 
     * @param subscriber
     *            The consumer of events
     */
    void subscribe(Consumer<IDeviceEvent> subscriber);

    /**
     * Listen for events broadcast on this event bus. Only events accepted by
     * the given filter will be sent to this subscriber.
     * 
     * @param subscriber
     *            the consumer of events
     * @param filter
     *            rule for which events to listen for
     */
    void subscribe(Consumer<IDeviceEvent> subscriber, Predicate<IDeviceEvent> filter);

    /**
     * Gets the region for this this DeviceEventBus. Larger or more complex
     * collections of devices can be split up into regions. Events can be
     * filtered by region to isolate certain devices, or to keep the number of
     * events to a minimum
     * 
     * @return the region of this bus
     */
    String getRegion();

}
