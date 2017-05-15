package net.realmproject.dcm.features.statefulness;


import net.realmproject.dcm.features.stateful.StatefulDevice;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.impl.sender.IParcelSender;


public class TestStatefulDevice extends IParcelSender implements StatefulDevice<TestState> {

    TestState state = new TestState();

    public TestStatefulDevice(String id, ParcelReceiver receiver) {
        super(id, receiver);
    }

    @Override
    public TestState getState() {
        return state;
    }

}
