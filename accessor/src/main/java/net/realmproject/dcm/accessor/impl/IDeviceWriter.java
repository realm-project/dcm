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

package net.realmproject.dcm.accessor.impl;


import java.io.Serializable;

import net.realmproject.dcm.accessor.DeviceWriter;
import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;


public class IDeviceWriter implements DeviceWriter {

    protected DeviceEventBus bus;
    private String id;

    public IDeviceWriter(String id, DeviceEventBus bus) {
        this.id = id;
        this.bus = bus;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void write(Serializable input) {
        String label = null;
        send(getId(), bus, DeviceEventType.VALUE_SET, input);
    }

    static boolean query(String deviceId, DeviceEventBus bus) {
        return send(deviceId, bus, DeviceEventType.VALUE_GET, null);
    }

    protected static boolean send(String deviceId, DeviceEventBus bus, DeviceEventType type, Serializable input) {
        if (bus == null) return false;

        DeviceEvent event;

        if (type == DeviceEventType.VALUE_SET) {
            event = new IDeviceEvent(type, deviceId, input);
        } else {
            event = new IDeviceEvent(type, deviceId);
        }

        return bus.broadcast(event);
    }
}
