package net.realmproject.dcm.features.statefulness;


import java.util.concurrent.BlockingQueue;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.bus.IDeviceEventBus;

import org.junit.Assert;
import org.junit.Test;


public class StatefulnessTest {

    @Test
    public void statefulnessTest() throws InterruptedException {

        DeviceEventBus bus = new IDeviceEventBus();
        BlockingQueue<DeviceEvent> eventQueue = bus.subscriptionQueue();

        TestStatefulDevice device = new TestStatefulDevice("id", bus);
        device.getState().number = 7;
        device.publishState();

        DeviceEvent event = eventQueue.take();

        TestState state = (TestState) event.getPayload();
        Assert.assertEquals(7, state.number);

    }

}
