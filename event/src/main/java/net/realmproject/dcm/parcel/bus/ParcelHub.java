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

package net.realmproject.dcm.parcel.bus;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Predicate;

import net.realmproject.dcm.parcel.ParcelNode;
import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.receiver.ParcelReceiver;
import net.realmproject.dcm.parcel.relay.IParcelRelay;


/**
 * A ParcelHub is a component which can {@link Parcel}s from any
 * source to all parcel nodes which subscribe to it. This provides a looser
 * coupling than a traditional direct listener-based model, as consumers don't
 * need to be aware of and registered with every producer of parcels. <br>
 * <br>
 * It is also possible to split a larger collection of devices into zones by
 * using more than one ParcelHub. Parcels from one bus can be selectively
 * propagated to another using a {@link IParcelRelay} or using a
 * distributed messaging system like ActiveMQ (see the network and network-mq
 * packages) <br>
 * <br>
 * 
 * @author NAS
 *
 */

public interface ParcelHub extends ParcelReceiver, ParcelNode {

    /**
     * Listen for parcels broadcast on this parcelhub. All parcels will be sent to
     * this subscriber.
     * 
     * @param subscriber
     *            The consumer of parcels
     */
    void subscribe(Consumer<Parcel> subscriber);

    /**
     * Listen for parcels broadcast on this parcel hub. Only parcels accepted by
     * the given filter will be sent to this subscriber.
     * 
     * @param filter
     *            rule for which parcels to listen for
     * @param subscriber
     *            the consumer of parcels
     */
    void subscribe(Predicate<Parcel> filter, Consumer<Parcel> subscriber);

    /**
     * Convenience method which wraps a call to
     * {@link ParcelHub#subscribe(Consumer)} so the {@link Parcel}s
     * are returned in a {@link BlockingQueue}. Users should take care to make
     * sure that the queue does not become backed up with parcels with large
     * payloads, as this could consume a large amount of memory
     * 
     * @return a {@link BlockingQueue} which will be populated with
     *         {@link Parcel}s
     */
    default BlockingQueue<Parcel> subscriptionQueue() {
        BlockingQueue<Parcel> queue = new LinkedBlockingQueue<>();
        subscribe(parcel -> queue.offer(parcel));
        return queue;
    }

    /**
     * Convenience method which wraps a call to
     * {@link ParcelHub#subscribe(Consumer)} so the {@link Parcel}s
     * are returned in a {@link BlockingQueue}. Users should take care to make
     * sure that the queue does not become backed up with parcels with large
     * payloads, as this could consume a large amount of memory
     * 
     * @param filter
     *            rule for which parcels to listen for
     * 
     * @return a {@link BlockingQueue} which will be populated with
     *         {@link Parcel}s
     */
    default BlockingQueue<Parcel> subscriptionQueue(Predicate<Parcel> filter) {
        BlockingQueue<Parcel> queue = new LinkedBlockingQueue<>();
        subscribe(filter, parcel -> queue.offer(parcel));
        return queue;
    }

    /**
     * Gets the zone for this this ParcelHub. Larger or more complex
     * collections of devices can be split up into zones. Parcels can be filtered
     * by zone to isolate certain devices, or to keep the number of parcels to a
     * minimum
     * 
     * @return the zone of this bus
     */
    String getZone();

    /**
     * Sets the zone for this this ParcelHub. Larger or more complex
     * collections of devices can be split up into zones. Parcels can be filtered
     * by zone to isolate certain devices, or to keep the number of parcels to a
     * minimum
     * 
     * @param zone
     *            the new zone this bus is in.
     */
    void setZone(String zone);

}
