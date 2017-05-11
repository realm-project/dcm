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

package net.realmproject.dcm.parcel.impl.link;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelLink;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.hub.ParcelHub;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;

/**
 * Relays parcels to a {@link ParcelReceiver}. Optionally accepts a
 * {@link ParcelHub} to subscribe to for parcels.
 * 
 * @author NAS
 *
 */
public class IParcelLink extends IParcelNode implements ParcelLink {

	protected ParcelReceiver receiver;

	
	public IParcelLink(){}
	
	public IParcelLink(ParcelHub from) {
		from.subscribe(this);
	}
	


	@Override
	public void send(Parcel<?> parcel) {
		if (!parcel.visit(getId())) { return; } // cycle detection
		if (receiver != null) {
			receiver.receive(parcel);
		}
	}

	@Override
	public ParcelReceiver getReceiver() {
		return receiver;
	}

	@Override
	public void setReceiver(ParcelReceiver receiver) {
		this.receiver = receiver;
	}

	
	
	
	
}
