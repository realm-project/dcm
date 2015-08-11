/**
 * Copyright 2014 Realm Project
 * 
 * This file is part of the Device Control Module (DCM).
 * 
 * DCM is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DCM is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DCM. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package net.realmproject.dcm.event.bus;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventNode;
import net.realmproject.dcm.event.receiver.DeviceEventReceiver;
import net.realmproject.dcm.event.relay.IDeviceEventRelay;


/**
 * A DeviceEventBus is a component which can have {@link DeviceEvent}s broadcast
 * from any source to the components which subscribe to it. This provides a
 * looser coupling than a traditional direct listener-based model, as consumers
 * don't need to be aware of and registered with every producer of events. <br>
 * <br>
 * It is also possible to split a larger collection of devices into zones by
 * using more than one DeviceEventBus. Events from one bus can be selectively
 * propagated to another using an {@link IDeviceEventRelay} or using a
 * distributed messaging system like ActiveMQ (see
 * {@link AbstractDeviceMessageEncoder} and {@link DeviceMessageDecoder}). <br>
 * <br>
 * A DeviceEventBus has a zone property which allows splitting busses into
 * logical groups not related to the actual bus topology. DeviceEventBusses
 * should not broadcast events from other zones if those events are marked
 * private.
 * 
 * @author NAS
 *
 */

public interface DeviceEventBus extends DeviceEventReceiver, DeviceEventNode {

    /**
     * Listen for events broadcast on this event bus. All events will be sent to
     * this subscriber.
     * 
     * @param subscriber
     *            The consumer of events
     */
    void subscribe(Consumer<DeviceEvent> subscriber);

    /**
     * Listen for events broadcast on this event bus. Only events accepted by
     * the given filter will be sent to this subscriber.
     * 
     * @param filter
     *            rule for which events to listen for
     * @param subscriber
     *            the consumer of events
     */
    void subscribe(Predicate<DeviceEvent> filter, Consumer<DeviceEvent> subscriber);

    /**
     * Convenience method which wraps a call to
     * {@link DeviceEventBus#subscribe(Consumer)} so the {@link DeviceEvent}s
     * are returned in a {@link BlockingQueue}. Users should take care to make
     * sure that the queue does not become backed up with events with large
     * payloads, as this could consume a large amount of memory
     * 
     * @return a {@link BlockingQueue} which will be populated with
     *         {@link DeviceEvent}s
     */
    default BlockingQueue<DeviceEvent> subscriptionQueue() {
        BlockingQueue<DeviceEvent> queue = new LinkedBlockingQueue<>();
        subscribe(event -> {
            System.out.println("Adding event " + event.getPayload());
            queue.offer(event);
        });
        return queue;
    }

    /**
     * Convenience method which wraps a call to
     * {@link DeviceEventBus#subscribe(Consumer)} so the {@link DeviceEvent}s
     * are returned in a {@link BlockingQueue}. Users should take care to make
     * sure that the queue does not become backed up with events with large
     * payloads, as this could consume a large amount of memory
     * 
     * @param filter
     *            rule for which events to listen for
     * 
     * @return a {@link BlockingQueue} which will be populated with
     *         {@link DeviceEvent}s
     */
    default BlockingQueue<DeviceEvent> subscriptionQueue(Predicate<DeviceEvent> filter) {
        BlockingQueue<DeviceEvent> queue = new LinkedBlockingQueue<>();
        subscribe(filter, event -> queue.offer(event));
        return queue;
    }

    /**
     * Gets the zone for this this DeviceEventBus. Larger or more complex
     * collections of devices can be split up into zones. Events can be filtered
     * by zone to isolate certain devices, or to keep the number of events to a
     * minimum
     * 
     * @return the zone of this bus
     */
    String getZone();

}
