package net.realmproject.dcm.device.test.values;


import java.io.Serializable;

import net.realmproject.dcm.device.ValueDevice;
import net.realmproject.dcm.event.bus.DeviceEventBus;


public class TestValuesDevice extends ValueDevice {

    Serializable value;

    public TestValuesDevice(String id, DeviceEventBus bus) {
        super(id, bus);
    }

    @Override
    public Serializable getValue() {
        return value;
    }

    @Override
    public void setValue(Serializable val) {
        value = val;
        publishValue();
    }

}
