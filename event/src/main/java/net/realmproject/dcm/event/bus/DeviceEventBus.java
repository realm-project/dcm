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


import java.util.function.Consumer;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.sender.DeviceEventSender;


/**
 * A DeviceEventBus is a component which can have {@link DeviceEvent}s broadcast
 * from any source to the components which subscribe to it. This provides a
 * looser coupling than a traditional direct listener-based model, as consumers
 * don't need to be aware of and registered with every producer of events. <br>
 * <br>
 * It is also possible to split a larger collection of devices into regions by
 * using more than one DeviceEventBus. Events from one bus can be selectively
 * propagated to another using an {@link IDeviceEventBusForwarder} or using a
 * distributed messaging system like ActiveMQ (see
 * {@link AbstractDeviceMessageEncoder} and {@link DeviceMessageDecoder}). <br>
 * <br>
 * A DeviceEventBus has a region property which allows splitting busses into
 * logical groups not related to the actual bus topology. DeviceEventBusses
 * should not broadcast events from other regions if those events are marked
 * private.
 * 
 * @author NAS
 *
 */

public interface DeviceEventBus extends DeviceEventSender {

    /**
     * Send a {@link DeviceEvent} to all subscribers of this bus. Broadcasting
     * is done asynchronously.
     * 
     * @param event
     *            The event to broadcast
     * @return true if event is accepted for broadcast, false otherwise.
     */
    boolean broadcast(DeviceEvent event);

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
     * @param filter
     *            rule for which events to listen for
     * @param subscriber
     *            the consumer of events
     */
    void subscribe(Predicate<DeviceEvent> filter, Consumer<DeviceEvent> subscriber);

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
