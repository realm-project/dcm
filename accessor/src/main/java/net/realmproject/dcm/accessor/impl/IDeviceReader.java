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
import java.util.LinkedHashMap;
import java.util.Map;

import net.realmproject.dcm.accessor.DeviceReader;
import net.realmproject.dcm.accessor.DeviceRecorder;
import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.Filters;


public class IDeviceReader extends LinkedHashMap<String, Serializable> implements DeviceReader {

    private String id;
    private Date timestamp;
    private DeviceRecorder recorder;
    private DeviceEventBus bus;

    public IDeviceReader(String id, DeviceEventBus bus) {
        this(id, bus, new DummyDeviceRecorder());
    }

    public IDeviceReader(String id, DeviceEventBus bus, DeviceRecorder recorder) {
        this.id = id;
        this.recorder = recorder;
        timestamp = new Date();
        this.bus = bus;
        bus.subscribe(this::handleEvent, Filters.filter().id(id).changedEvents().booleanAnd());
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
        putAll(state);

        recorder.recordState(this);
    }

    @Override
    public String getDeviceId() {
        return id;
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public void query() {
        IDeviceWriter.query(getDeviceId(), bus);
    }

    @Override
    public Map<String, Serializable> getState() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        return o == this;
    }

}