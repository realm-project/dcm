package net.realmproject.dcm.accessor.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import net.realmproject.dcm.accessor.DeviceReader;
import net.realmproject.dcm.accessor.DeviceWriter;
import net.realmproject.dcm.event.bus.DeviceEventBus;


public class IDeviceReaderWriter implements DeviceReader, DeviceWriter {

    DeviceReader reader;
    DeviceWriter writer;

    public IDeviceReaderWriter(String id, DeviceEventBus bus) {
        reader = new IDeviceReader(id, bus);
        writer = new IDeviceWriter(id, bus);
    }

    @Override
    public String getId() {
        return reader.getId();
    }

    @Override
    public void write(Serializable input) {
        writer.write(input);
    }

    @Override
    public Map<String, Serializable> getState() {
        return reader.getState();
    }

    @Override
    public Date getTimestamp() {
        return reader.getTimestamp();
    }

    @Override
    public void query() {
        reader.query();
    }

}
