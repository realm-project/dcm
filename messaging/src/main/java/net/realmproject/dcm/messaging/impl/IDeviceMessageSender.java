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

package net.realmproject.dcm.messaging.impl;


import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.AcceptFilter;
import net.realmproject.dcm.messaging.DeviceMessage;
import net.realmproject.dcm.messaging.DeviceMessageSender;
import net.realmproject.dcm.messaging.Transcoder;


/**
 * Listens for events on a {@link DeviceEventBus} and transmits
 * {@link DeviceMessage}s on a distributed messaging system (eg ActiveMQ)
 * 
 * @author NAS
 *
 */
public abstract class IDeviceMessageSender implements DeviceMessageSender {

    private Predicate<DeviceEvent> filter = new AcceptFilter();
    protected Transcoder transcoder;

    public IDeviceMessageSender(DeviceEventBus bus, Transcoder transcoder) {
        this.transcoder = transcoder;
        bus.subscribe(event -> {
            if (!filter.test(event)) { return; }
            send(new DeviceMessage(event));
        });
    }

    public Predicate<DeviceEvent> getFilter() {
        return filter;
    }

    public void setFilter(Predicate<DeviceEvent> filter) {
        this.filter = filter;
    }

}
