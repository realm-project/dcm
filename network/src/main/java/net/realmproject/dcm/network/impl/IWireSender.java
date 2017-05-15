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

package net.realmproject.dcm.network.impl;


import net.realmproject.dcm.network.WireSender;
import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.hub.ParcelHub;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;


/**
 * Listens for parcels on a {@link ParcelHub} and transmits
 * {@link WireMessage}s on a distributed messaging system (eg ActiveMQ)
 * 
 * @author NAS
 *
 */
public abstract class IWireSender extends IParcelNode implements WireSender {

    @Override
    public void receive(Parcel<?> parcel) {
        if (parcel.getPath().contains(getId())) { return; } // cycle detection
        parcel.getPath().add(getId());
        send(parcel.serializeParcel());
    }

}