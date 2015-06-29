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

package net.realmproject.dcm.event.bus;


import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.filter.AcceptFilter;
import net.realmproject.dcm.event.source.AbstractDeviceEventSource;


/**
 * Forwards events from one DeviceEventBus to another, applying the optional
 * filter to control which messages are allowed through.
 * 
 * @author NAS
 *
 */
public class IDeviceEventBusForwarder extends AbstractDeviceEventSource {

    private DeviceEventBus to;

    public IDeviceEventBusForwarder(DeviceEventBus from, DeviceEventBus to) {
        this(from, to, new AcceptFilter());
        startSending();
    }

    public IDeviceEventBusForwarder(DeviceEventBus from, DeviceEventBus to, Predicate<DeviceEvent> filter) {
        this.to = to;
        from.subscribe(filter, this::send);
    }

    @Override
    protected boolean doSend(DeviceEvent event) {
        return to.broadcast(event);
    }
}
