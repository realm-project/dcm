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

package net.realmproject.dcm.device;


import java.io.Serializable;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.composite.BooleanAndFilter;
import net.realmproject.dcm.event.filter.deviceid.DeviceIDWhitelistFilter;
import net.realmproject.dcm.event.filter.devicemessagetype.ValueGetFilter;
import net.realmproject.dcm.event.filter.devicemessagetype.ValueSetFilter;
import net.realmproject.dcm.messaging.DeviceMessageType;


public abstract class SimpleDevice extends Device {

    public SimpleDevice(String id, DeviceEventBus bus) {
        super(id, bus);
        bus.subscribe(this::onSet, new BooleanAndFilter(new DeviceIDWhitelistFilter(getId()), new ValueSetFilter()));
        bus.subscribe(this::onGet, new BooleanAndFilter(new DeviceIDWhitelistFilter(getId()), new ValueGetFilter()));
    }

    private void onSet(DeviceEvent e) {
        setValue(e.getValue());
    }

    private void onGet(DeviceEvent e) {
        send(new IDeviceEvent(DeviceMessageType.VALUE_CHANGED, getId(), (Serializable) getValue()));
    }

}
