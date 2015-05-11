package net.realmproject.dcm.accessor.commands.impl;


import java.io.Serializable;

import net.realmproject.dcm.accessor.commands.DeviceCommander;
import net.realmproject.dcm.accessor.commands.DeviceRecorder;
import net.realmproject.dcm.accessor.impl.IDeviceWriter;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.features.command.Command;
import net.realmproject.dcm.util.DCMSerialize;


public class IDeviceCommander extends IDeviceWriter implements DeviceCommander {

    private DeviceRecorder recorder;

    public IDeviceCommander(String id, DeviceEventBus bus) {
        this(id, bus, new DummyDeviceRecorder());
    }

    public IDeviceCommander(String id, DeviceEventBus bus, DeviceRecorder recorder) {
        super(id, bus);
        this.recorder = recorder;
    }

    @Override
    public String write(Command command) {
        String label = null;
        if (command.record) {
            label = recorder.recordCommand(command);
        }
        Serializable input = (Serializable) DCMSerialize.structToMap(command);
        send(getId(), bus, DeviceEventType.VALUE_SET, input);
        return label;
    }

}
