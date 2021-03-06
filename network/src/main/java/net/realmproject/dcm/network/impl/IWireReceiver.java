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

import net.realmproject.dcm.network.WireReceiver;
import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.impl.sender.IParcelSender;

/**
 * Receives wire messages from a network-based source (eg sockets) and publishes
 * them to the given {@link ParcelReceiver}
 * 
 * @author NAS
 *
 */
public class IWireReceiver extends IParcelSender implements WireReceiver {

	public IWireReceiver(ParcelReceiver receiver) {
		super(receiver);
	}
	
    public void wireReceive(byte[] serializedParcel) {
    	Parcel<?> parcel = Parcel.deserializeParcel(serializedParcel);
        send(parcel);
    }

}
