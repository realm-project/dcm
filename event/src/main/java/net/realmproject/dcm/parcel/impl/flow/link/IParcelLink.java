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

package net.realmproject.dcm.parcel.impl.flow.link;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.flow.hub.ParcelHub;
import net.realmproject.dcm.parcel.core.flow.link.ParcelLink;
import net.realmproject.dcm.parcel.core.linkable.SingleLinkable;
import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.parcel.core.metadata.ParcelNodeType;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;

/**
 * Relays parcels to a {@link ParcelReceiver}. Optionally accepts a
 * {@link ParcelHub} to subscribe to for parcels.
 * 
 * @author NAS
 *
 */
@ParcelMetadata (name="Link", type=ParcelNodeType.OTHER)
public class IParcelLink extends IParcelNode implements ParcelLink {

	protected ParcelReceiver receiver;

	
	public IParcelLink(){}
	


	@Override
	public void send(Parcel<?> parcel) {
		if (!parcel.visit(getId())) { return; } // cycle detection
		if (receiver != null) {
			receiver.receive(parcel);
		}
	}


	public ParcelReceiver getReceiver() {
		return receiver;
	}


	public void setReceiver(ParcelReceiver receiver) {
		this.receiver = receiver;
	}



	@Override
	public SingleLinkable link(SingleLinkable link) {
		setReceiver(link);
		return this;
	}



	@Override
	public void link(ParcelReceiver receiver) {
		setReceiver(receiver);
	}



	@Override
	public void unlink() {
		this.receiver = null;
	}



	@Override
	public ParcelReceiver getLink() {
		return receiver;
	}

	
	
	
	
}
