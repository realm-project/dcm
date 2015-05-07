package net.realmproject.dcm.accessor.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import net.realmproject.dcm.accessor.DeviceReader;
import net.realmproject.dcm.accessor.DeviceRecorder;
import net.realmproject.dcm.accessor.DeviceWriter;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.features.command.Command;


public class IDeviceReaderWriter implements DeviceReader, DeviceWriter {

    DeviceReader reader;
    DeviceWriter writer;

    public IDeviceReaderWriter(String id, DeviceEventBus bus) {
        reader = new IDeviceReader(id, bus);
        writer = new IDeviceWriter(id, bus);
    }

    public IDeviceReaderWriter(String id, DeviceEventBus bus, DeviceRecorder recorder) {
        reader = new IDeviceReader(id, bus, recorder);
        writer = new IDeviceWriter(id, bus, recorder);
    }

    @Override
    public String getDeviceId() {
        return reader.getDeviceId();
    }

    @Override
    public String write(Command command) {
        return writer.write(command);
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
