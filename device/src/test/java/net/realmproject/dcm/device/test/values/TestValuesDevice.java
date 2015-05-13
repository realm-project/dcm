package net.realmproject.dcm.device.test.values;


import net.realmproject.dcm.device.Device;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.features.Values;


public class TestValuesDevice extends Device implements Values {

    Object value;

    public TestValuesDevice(String id, DeviceEventBus bus) {
        super(id, bus);
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object val) {
        value = val;
        publishValue();
    }

}
