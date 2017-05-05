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

package net.realmproject.dcm.parcel.flow.link;


import net.realmproject.dcm.parcel.flow.hub.ParcelHub;
import net.realmproject.dcm.parcel.node.IParcelNode;
import net.realmproject.dcm.parcel.node.receiver.ParcelReceiver;

import net.realmproject.dcm.parcel.Parcel;


/**
 * Relays parcels to a {@link ParcelReceiver}. Optionally accepts a
 * {@link ParcelHub} to subscribe to for parcels.
 * 
 * @author NAS
 *
 */
public class IParcelLink extends IParcelNode implements ParcelLink {

    protected ParcelReceiver to;

    public IParcelLink(ParcelReceiver to) {
        this.to = to;
    }
    
    public IParcelLink(ParcelHub from, ParcelReceiver to) {
        this(to);
        from.subscribe(this);
    }

    @Override
    public void receive(Parcel<?> parcel) {
        if (!filter(parcel)) { return; }
        if (!parcel.visit(getId())) { return; } //cycle detection
        parcel = transform(parcel);
        if (to != null) {
        	to.receive(parcel);
        }
    }

}
