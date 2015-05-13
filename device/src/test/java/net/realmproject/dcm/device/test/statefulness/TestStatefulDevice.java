package net.realmproject.dcm.device.test.statefulness;


import net.realmproject.dcm.device.Device;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.features.Statefulness;


public class TestStatefulDevice extends Device implements Statefulness<TestState> {

    TestState state = new TestState();

    public TestStatefulDevice(String id, DeviceEventBus bus) {
        super(id, bus);
    }

    @Override
    public TestState getState() {
        return state;
    }

}
