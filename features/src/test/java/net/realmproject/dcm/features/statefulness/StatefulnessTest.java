package net.realmproject.dcm.features.statefulness;


import java.util.concurrent.BlockingQueue;

import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.flow.hub.IParcelHub;
import net.realmproject.dcm.parcel.flow.hub.ParcelHub;
import net.realmproject.dcm.parcel.node.receiver.ParcelReceiverQueue;

import org.junit.Assert;
import org.junit.Test;


public class StatefulnessTest {

    @Test
    public void statefulnessTest() throws InterruptedException {

    	ParcelHub bus = new IParcelHub();
        ParcelReceiverQueue eventQueue = new ParcelReceiverQueue();
        bus.subscribe(eventQueue);

        TestStatefulDevice device = new TestStatefulDevice("id", bus);
        device.getState().number = 7;
        device.publishState();

        Parcel<?> event = eventQueue.take();

        TestState state = (TestState) event.getPayload();
        Assert.assertEquals(7, state.number);

    }

}
