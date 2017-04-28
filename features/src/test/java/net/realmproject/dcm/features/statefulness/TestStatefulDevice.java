package net.realmproject.dcm.features.statefulness;


import net.realmproject.dcm.features.stateful.StatefulDevice;
import net.realmproject.dcm.parcel.Device;
import net.realmproject.dcm.parcel.receiver.ParcelReceiver;


public class TestStatefulDevice extends Device implements StatefulDevice<TestState> {

    TestState state = new TestState();

    public TestStatefulDevice(String id, ParcelReceiver receiver) {
        super(id, receiver);
    }

    @Override
    public TestState getState() {
        return state;
    }

}
