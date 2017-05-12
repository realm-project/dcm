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

package net.realmproject.dcm.parcel.core.hub;



import java.util.function.Predicate;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelNode;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;
import net.realmproject.dcm.parcel.core.filter.ParcelFilterer;
import net.realmproject.dcm.parcel.impl.link.IParcelLink;


/**
 * A ParcelHub is a component which can {@link Parcel}s from any
 * source to all parcel nodes which subscribe to it. This provides a looser
 * coupling than a traditional direct listener-based model, as consumers don't
 * need to be aware of and registered with every producer of parcels. <br>
 * <br>
 * It is also possible to split a larger collection of devices into zones by
 * using more than one ParcelHub. Parcels from one bus can be selectively
 * propagated to another using a {@link IParcelLink} or using a
 * distributed messaging system like ActiveMQ (see the network and network-mq
 * packages) <br>
 * <br>
 * 
 * @author NAS
 *
 */

public interface ParcelHub extends ParcelReceiver, ParcelNode, ParcelFilterer, ParcelSender {

    /**
     * Listen for parcels broadcast on this parcelhub. All parcels will be sent to
     * this subscriber.
     * 
     * @param subscriber
     *            The consumer of parcels
     */
    void subscribe(ParcelReceiver subscriber);


    /**
     * Listen for parcels broadcast on this parcel hub. Only parcels accepted by
     * the given filter will be sent to this subscriber.
     * 
     * @param filter
     *            rule for which parcels to listen for
     * @param subscriber
     *            the consumer of parcels
     */
    void subscribe(Predicate<Parcel<?>> filter, ParcelReceiver subscriber);



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


	void setCopying(Boolean copying);


	Boolean isCopying();

}
