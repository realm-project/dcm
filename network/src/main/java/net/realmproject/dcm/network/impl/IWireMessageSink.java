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


import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.receiver.DeviceEventReceiver;
import net.realmproject.dcm.event.source.AbstractDeviceEventSource;
import net.realmproject.dcm.event.source.DeviceEventSource;
import net.realmproject.dcm.network.WireMessage;
import net.realmproject.dcm.network.WireMessageSink;
import net.realmproject.dcm.network.transcoder.Transcoder;


/**
 * Receives {@link WireMessage}s from a distributed messaging system (eg
 * ActiveMQ) and publishes them to the given {@link DeviceEventBus}
 * 
 * @author NAS
 *
 */
public class IWireMessageSink extends AbstractDeviceEventSource implements WireMessageSink, DeviceEventSource {

    private Transcoder transcoder;

    public IWireMessageSink(DeviceEventReceiver receiver, Transcoder transcoder) {
        super(receiver);
        this.transcoder = transcoder;
    }

    @Override
    public void receive(WireMessage deviceMessage) {
        DeviceEvent deviceEvent = deviceMessage.getEvent();
        send(deviceEvent);
    }

    /**
     * Retrieve the {@link WireMessage} {@link Transcoder}. This is the component used to adapt to other wire formats by de/encoding {@link WireMessage}s
     * @return the current transcoder
     */
    public Transcoder getTranscoder() {
        return transcoder;
    }

    /**
     * Sets the {@link WireMessage} {@link Transcoder}. This is the component used to adapt to other wire formats by de/encoding {@link WireMessage}s
     * @param transcoder
     */
    public void setTranscoder(Transcoder transcoder) {
        this.transcoder = transcoder;
    }

}
