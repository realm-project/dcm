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
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;

/**
 * Receives {@link Parcel}s via the {@link ParcelReceiver} interface and
 * transmits them via a network-based source (eg sockets)
 * 
 * @author NAS
 *
 */
public abstract class IWireSender extends IParcelNode implements WireSender {

    public void receive(Parcel<?> parcel) {
        if (parcel.getPath().contains(getId())) { return; } // cycle detection
        parcel.getPath().add(getId());
        wireSend(parcel.serializeParcel());
    }
	
}
