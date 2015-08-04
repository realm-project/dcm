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

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.receiver.DeviceEventReceiver;
import net.realmproject.dcm.event.source.AbstractDeviceEventSource;
import net.realmproject.dcm.event.source.DeviceEventSource;
import net.realmproject.dcm.network.WireMessage;
import net.realmproject.dcm.network.WireMessageSink;
import net.realmproject.dcm.network.transcoder.Transcoder;
import net.realmproject.dcm.network.transcoder.WireMessageTranscoder;


/**
 * Receives {@link WireMessage}s from a distributed messaging system (eg
 * ActiveMQ) and publishes them to the given {@link DeviceEventBus}
 * 
 * @author NAS
 *
 */
public class IWireMessageSink extends AbstractDeviceEventSource implements WireMessageSink, DeviceEventSource, WireMessageTranscoder {

    private Transcoder<WireMessage, Serializable> transcoder;

	public IWireMessageSink(DeviceEventReceiver receiver, Transcoder<WireMessage, Serializable> transcoder) {
		super(receiver);
        this.transcoder = transcoder;
    }

    @Override
    public void receive(WireMessage deviceMessage) {
        DeviceEvent deviceEvent = deviceMessage.getEvent();
        send(deviceEvent);
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
