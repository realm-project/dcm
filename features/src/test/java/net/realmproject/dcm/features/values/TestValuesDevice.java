package net.realmproject.dcm.features.values;


import java.io.Serializable;

import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.features.value.AbstractValueDevice;


public class TestValuesDevice extends AbstractValueDevice {

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
