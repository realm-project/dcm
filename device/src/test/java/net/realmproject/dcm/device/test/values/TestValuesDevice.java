package net.realmproject.dcm.device.test.values;


import net.realmproject.dcm.device.ValueDevice;
import net.realmproject.dcm.event.bus.DeviceEventBus;


public class TestValuesDevice extends ValueDevice {

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
