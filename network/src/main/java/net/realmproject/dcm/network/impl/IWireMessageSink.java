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


import java.io.Serializable;

import net.realmproject.dcm.network.WireMessage;
import net.realmproject.dcm.network.WireMessageSink;
import net.realmproject.dcm.network.transcoder.Transcoder;
import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.bus.ParcelHub;
import net.realmproject.dcm.parcel.publisher.ParcelPublisher;
import net.realmproject.dcm.parcel.publisher.IParcelPublisher;
import net.realmproject.dcm.parcel.receiver.ParcelReceiver;


/**
 * Receives {@link WireMessage}s from a distributed messaging system (eg
 * ActiveMQ) and publishes them to the given {@link ParcelHub}
 * 
 * @author NAS
 *
 */
public class IWireMessageSink extends IParcelPublisher implements WireMessageSink, ParcelPublisher {

    private Transcoder<WireMessage, Serializable> transcoder;

    public IWireMessageSink(ParcelReceiver receiver, Transcoder<WireMessage, Serializable> transcoder) {
        super(receiver);
        this.transcoder = transcoder;
    }

    @Override
    public void receive(WireMessage deviceMessage) {
        Parcel<?> parcel = deviceMessage.getParcel();
        publish(parcel);
    }

    @Override
    public Transcoder<WireMessage, Serializable> getTranscoder() {
        return transcoder;
    }

    @Override
    public void setTranscoder(Transcoder<WireMessage, Serializable> transcoder) {
        this.transcoder = transcoder;
    }

}
