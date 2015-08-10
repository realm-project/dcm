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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import net.realmproject.dcm.accessor.DeviceAccessor;
import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.Logging;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.FilterBuilder;
import net.realmproject.dcm.event.receiver.DeviceEventReceiver;


public class IDeviceAccessor<T extends Serializable> implements DeviceAccessor<T>, Logging {

    protected DeviceEventReceiver receiver;
    private String id;
    private String deviceId;
    private Date timestamp = new Date();
    private T deviceState;

    private List<Consumer<T>> listeners = new ArrayList<>();

    public IDeviceAccessor(String id, String deviceId, DeviceEventBus bus) {
        this.id = id;
        this.deviceId = deviceId;
        this.receiver = bus;

        // listen for change events, query device to produce one
        bus.subscribe(FilterBuilder.start().source(deviceId).eventChange(), this::handleEvent);
        sendValueGet();

    }

    @SuppressWarnings("unchecked")
    public void handleEvent(DeviceEvent event) {

        // will we ever have the issue where messages arrive out of order? What
        // if they contain different key->value mappings?
        if (event.getTimestamp() != null && event.getTimestamp().after(timestamp)) {
            timestamp = event.getTimestamp();
        } else {
            return;
        }

        try {
            deviceState = (T) event.getPayload();
            for (Consumer c : new ArrayList<>(listeners)) {
                c.accept(deviceState);
            }
        }
        catch (ClassCastException e) {
            getLog().error(e);
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void sendMessage(Serializable input) {
        publish(DeviceEventType.MESSAGE, input);
    }

    @Override
    public void sendValueSet(Serializable input) {
        publish(DeviceEventType.VALUE_SET, input);
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public T getState() {
        return deviceState;
    }

    public void sendValueGet() {
        publish(DeviceEventType.VALUE_GET);
    }

    protected void publish(DeviceEventType type) {
        publish(type, null);
    }

    protected void publish(DeviceEventType type, Serializable payload) {
        publish(type, getId(), getTargetId(), payload);
    }

    @Override
    public void publish(DeviceEvent event) {
        if (receiver == null) return;
        receiver.accept(event);
    }

    @Override
    public List<Consumer<T>> getListeners() {
        return listeners;
    }

    @Override
    public void setListeners(List<Consumer<T>> listeners) {
        this.listeners = listeners;
    }

    @Override
    public String getTargetId() {
        return deviceId;
    }

    @Override
    public void setTargetId(String deviceId) {
        this.deviceId = deviceId;
    }

}
