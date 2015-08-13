package net.realmproject.dcm.features.statefulness;


import net.realmproject.dcm.event.Device;
import net.realmproject.dcm.event.receiver.DeviceEventReceiver;
import net.realmproject.dcm.features.stateful.StatefulDevice;


public class TestStatefulDevice extends Device implements StatefulDevice<TestState> {

    TestState state = new TestState();

    public TestStatefulDevice(String id, DeviceEventReceiver receiver) {
        super(id, receiver);
    }

    @Override
    public TestState getState() {
        return state;
    }

}
