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

import net.realmproject.dcm.parcel.node.IParcelNode;
import net.realmproject.dcm.parcel.node.ParcelNode;
import net.realmproject.dcm.parcel.node.receiver.ParcelReceiver;
import net.realmproject.dcm.parcel.Logging;
import net.realmproject.dcm.parcel.Parcel;


/**
 * 
 * @author maxweld, NAS
 *
 */

public class IParcelPublisher extends IParcelNode implements ParcelNode, ParcelPublisher, Logging {

    private ParcelReceiver receiver;

    public IParcelPublisher(String id, ParcelReceiver receiver) {
    	this(receiver);
    	setId(id);
    }
    
    public IParcelPublisher(ParcelReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void publish(Parcel<?> parcel) {
        getLog().trace("publish() called for " + parcel + " from " + this.getId());
        if (parcel == null) { return; }
        getLog().trace("Publishing parcel " + parcel + " from " + this.getId());
        receiver.receive(parcel);
        getLog().trace("Parcel " + parcel + " Published from " + this.getId());
    }

}
