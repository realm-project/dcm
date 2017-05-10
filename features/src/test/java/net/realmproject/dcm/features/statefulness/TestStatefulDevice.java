package net.realmproject.dcm.features.statefulness;


import net.realmproject.dcm.features.stateful.StatefulDevice;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.impl.publisher.IParcelPublisher;


public class TestStatefulDevice extends IParcelPublisher implements StatefulDevice<TestState> {

    TestState state = new TestState();

    public TestStatefulDevice(String id, ParcelReceiver receiver) {
        super(id, receiver);
    }

    @Override
    public TestState getState() {
        return state;
    }

}
