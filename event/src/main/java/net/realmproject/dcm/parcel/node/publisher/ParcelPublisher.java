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

package net.realmproject.dcm.parcel.node.publisher;


import net.realmproject.dcm.parcel.Parcel;


/**
 * A ParcelPublisher is anything which publishes parcel of its own.
 * 
 * @author NAS
 *
 */
public interface ParcelPublisher {

    /**
     * Sends the given {@link Parcel}. If this component is not currently
     * sending parcels (eg stopSending() has been called) this parcel will be
     * discarded.
     * 
     * @param parcel
     *            the parcel to send
     */
    void publish(Parcel<?> parcel);



}
