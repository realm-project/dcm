package net.realmproject.dcm.device.test.statefulness;


import net.realmproject.dcm.device.Device;
import net.realmproject.dcm.event.receiver.DeviceEventReceiver;
import net.realmproject.dcm.features.statefulness.Statefulness;


public class TestStatefulDevice extends Device implements Statefulness<TestState> {

    TestState state = new TestState();

    public TestStatefulDevice(String id, DeviceEventReceiver receiver) {
        super(id, receiver);
    }

    @Override
    public TestState getState() {
        return state;
    }

}
