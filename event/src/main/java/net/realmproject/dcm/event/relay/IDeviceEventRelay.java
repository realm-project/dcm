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

package net.realmproject.dcm.event.relay;


import java.util.List;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.DeviceEventFilterer;
import net.realmproject.dcm.event.filter.IDeviceEventFilterer;
import net.realmproject.dcm.event.filter.filters.AcceptFilter;
import net.realmproject.dcm.event.receiver.DeviceEventReceiver;


/**
 * Forwards events from one DeviceEventBus to another, applying the optional
 * filter to control which messages are allowed through.
 * 
 * @author NAS
 *
 */
public class IDeviceEventRelay extends IDeviceEventFilterer implements DeviceEventReceiver, DeviceEventFilterer {

    private DeviceEventReceiver to;

    public IDeviceEventRelay(DeviceEventBus from, DeviceEventReceiver to) {
        this(from, to, new AcceptFilter());
    }

    public IDeviceEventRelay(DeviceEventReceiver to, Predicate<DeviceEvent> filter) {
        this.to = to;
        setFilters(filter);
    }

    public IDeviceEventRelay(DeviceEventReceiver to, List<Predicate<DeviceEvent>> filters) {
        this.to = to;
        setFilters(filters);
    }

    public IDeviceEventRelay(DeviceEventBus from, DeviceEventReceiver to, Predicate<DeviceEvent> filter) {
        this(to, filter);
        from.subscribe(this::filter, this::accept);
    }

    public IDeviceEventRelay(DeviceEventBus from, DeviceEventReceiver to, List<Predicate<DeviceEvent>> filters) {
        this(to, filters);
        from.subscribe(this::filter, this::accept);
    }

    @Override
    public boolean accept(DeviceEvent event) {
        return filter(event) ? to.accept(event) : false;
    }
}
