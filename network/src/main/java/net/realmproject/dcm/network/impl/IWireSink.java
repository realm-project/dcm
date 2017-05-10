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


import net.realmproject.dcm.network.WireSink;
import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.node.hub.ParcelHub;
import net.realmproject.dcm.parcel.node.publisher.IParcelPublisher;
import net.realmproject.dcm.parcel.node.publisher.ParcelPublisher;
import net.realmproject.dcm.parcel.node.receiver.ParcelReceiver;


/**
 * Receives {@link WireMessage}s from a distributed messaging system (eg
 * ActiveMQ) and publishes them to the given {@link ParcelHub}
 * 
 * @author NAS
 *
 */
public class IWireSink extends IParcelPublisher implements WireSink, ParcelPublisher {


    public IWireSink(ParcelReceiver receiver) {
        super(receiver);
    }

    @Override
    public void receive(byte[] serializedParcel) {
    	Parcel<?> parcel = Parcel.deserializeParcel(serializedParcel);
        publish(parcel);
    }


}
