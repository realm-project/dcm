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
import net.realmproject.dcm.network.WireMessageSource;
import net.realmproject.dcm.network.transcoder.Transcoder;
import net.realmproject.dcm.network.transcoder.WireMessageTranscoding;
import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.bus.ParcelHub;
import net.realmproject.dcm.parcel.receiver.ParcelReceiver;
import net.realmproject.dcm.parcel.relay.AbstractParcelRelay;


/**
 * Listens for parcels on a {@link ParcelHub} and transmits
 * {@link WireMessage}s on a distributed messaging system (eg ActiveMQ)
 * 
 * @author NAS
 *
 */
public abstract class IWireMessageSource extends AbstractParcelRelay
        implements WireMessageSource, ParcelReceiver, WireMessageTranscoding {

    private Transcoder<WireMessage, Serializable> transcoder;

    public IWireMessageSource(ParcelHub bus, Transcoder<WireMessage, Serializable> transcoder) {
        this.transcoder = transcoder;
        bus.subscribe(this::filter, this::accept);
    }

    @Override
    public void accept(Parcel parcel) {
        if (parcel.getRoute().contains(getId())) { return; } // cycle detection
        parcel.getRoute().add(getId());
        send(new WireMessage(transform(parcel)));
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
