package net.realmproject.dcm.device.test.values;


import java.util.concurrent.BlockingQueue;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.bus.IDeviceEventBus;

import org.junit.Assert;
import org.junit.Test;


public class ValuesTest {

    @Test
    public void valuesTest() throws InterruptedException {

        DeviceEventBus bus = new IDeviceEventBus();
        BlockingQueue<DeviceEvent> events = bus.subscriptionQueue();
        TestValuesDevice device = new TestValuesDevice("id", bus);
        device.setValue(4);

        // test that getValue returns correct value
        Assert.assertEquals(4, device.getValue());

        // test that correct value is broadcast on bus
        DeviceEvent event = events.take();
        Assert.assertEquals(4, event.getValue());

    }

}
