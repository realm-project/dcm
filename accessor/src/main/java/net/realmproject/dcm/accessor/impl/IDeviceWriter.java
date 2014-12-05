package net.realmproject.dcm.accessor.impl;


import java.io.Serializable;

import net.realmproject.dcm.accessor.DeviceRecorder;
import net.realmproject.dcm.accessor.DeviceWriter;
import net.realmproject.dcm.command.Command;
import net.realmproject.dcm.command.CommandSerialize;
import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.messaging.DeviceMessageType;


public class IDeviceWriter implements DeviceWriter {

    private DeviceEventBus bus;
    private String id;
    private DeviceRecorder recorder;

    public IDeviceWriter(String id, DeviceEventBus bus) {
        this(id, bus, new DummyDeviceRecorder());
    }

    public IDeviceWriter(String id, DeviceEventBus bus, DeviceRecorder recorder) {
        this.id = id;
        this.bus = bus;
        this.recorder = recorder;
    }

    @Override
    public String getDeviceId() {
        return id;
    }

    @Override
    public String write(Command command) {
        String label = null;
        if (command.record) {
            label = recorder.recordCommand(id, command);
        }
        send(getDeviceId(), bus, DeviceMessageType.VALUE_SET, command);
        return label;
    }

    static boolean query(String deviceId, DeviceEventBus bus) {
        return send(deviceId, bus, DeviceMessageType.VALUE_GET, null);
    }

    private static boolean send(String deviceId, DeviceEventBus bus, DeviceMessageType type, Command command) {
        if (bus == null) return false;

        DeviceEvent event;

        if (type == DeviceMessageType.VALUE_SET) {
            event = new DeviceEvent(type, deviceId, (Serializable) CommandSerialize.structToMap(command));
        } else {
            event = new DeviceEvent(type, deviceId);
        }

        return bus.broadcast(event);
    }
}
