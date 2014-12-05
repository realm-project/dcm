package net.realmproject.dcm.accessor.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import net.realmproject.dcm.accessor.DeviceReader;
import net.realmproject.dcm.accessor.DeviceRecorder;
import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.composite.BooleanAndFilter;
import net.realmproject.dcm.event.filter.deviceid.DeviceIDWhitelistFilter;
import net.realmproject.dcm.event.filter.devicemessagetype.ValueChangedFilter;


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
        bus.subscribe(this::handleEvent,
                new BooleanAndFilter(new DeviceIDWhitelistFilter(id), new ValueChangedFilter()));
        query();
    }

    public void handleEvent(DeviceEvent event) {

        String targetId = event.getDeviceId();

        // only accept on events for this device
        // if (!id.equals(targetId)) return;

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

        recorder.recordState(id, this);
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