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


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.DeviceEventFilterer;
import net.realmproject.dcm.event.receiver.DeviceEventReceiver;
import net.realmproject.dcm.network.WireMessage;
import net.realmproject.dcm.network.WireMessageSource;
import net.realmproject.dcm.network.transcoder.Transcoder;


/**
 * Listens for events on a {@link DeviceEventBus} and transmits
 * {@link WireMessage}s on a distributed messaging system (eg ActiveMQ)
 * 
 * @author NAS
 *
 */
public abstract class IWireMessageSource implements WireMessageSource, DeviceEventReceiver, DeviceEventFilterer {

    private List<Predicate<DeviceEvent>> filters = new ArrayList<>();
    private Transcoder transcoder;

    public IWireMessageSource(DeviceEventBus bus, Transcoder transcoder) {
        this.transcoder = transcoder;
        bus.subscribe(this::filter, this::accept);
    }

    @Override
    public List<Predicate<DeviceEvent>> getFilters() {
        return filters;
    }

    @Override
    public synchronized void setFilters(List<Predicate<DeviceEvent>> filters) {
        this.filters.clear();
        this.filters.addAll(filters);
    }

    @Override
    public boolean accept(DeviceEvent event) {
        return send(new WireMessage(event));
    }

    public Transcoder getTranscoder() {
        return transcoder;
    }

    public void setTranscoder(Transcoder transcoder) {
        this.transcoder = transcoder;
    }

}
