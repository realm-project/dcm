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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.realmproject.dcm.accessor.DeviceAccessor;
import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.Filters;


public class IDeviceAccessor implements DeviceAccessor {

    protected DeviceEventBus bus;
    private String id;
    private Date timestamp = new Date();
    private Map<String, Serializable> deviceState = new HashMap<>();

    public IDeviceAccessor(String id, DeviceEventBus bus) {
        this.id = id;
        this.bus = bus;

        // listen for change events, query device to produce one
        bus.subscribe(Filters.id(id).and(Filters.changedEvents()), this::handleEvent);
        query();

    }

    public void handleEvent(DeviceEvent event) {

        // will we ever have the issue where messages arrive out of order? What
        // if they contain different key->value mappings?
        if (event.getTimestamp() != null && event.getTimestamp().after(timestamp)) {
            timestamp = event.getTimestamp();
        } else {
            return;
        }

        @SuppressWarnings("unchecked")
        Map<String, Serializable> state = (Map<String, Serializable>) event.getValue();
        deviceState.clear();
        deviceState.putAll(state);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void write(Serializable input) {
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

    @Override
    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public void query() {
        IDeviceAccessor.query(getId(), bus);
    }

    @Override
    public Map<String, Serializable> getState() {
        return deviceState;
    }

}
