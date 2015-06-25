package net.realmproject.dcm.device.test.values;


import java.util.concurrent.BlockingQueue;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.bus.IDeviceEventBus;
import net.realmproject.dcm.event.filter.deviceeventtype.ValueChangedFilter;

import org.junit.Assert;
import org.junit.Test;


public class ValuesTest {

    @Test
    public void valuesTest() throws InterruptedException {

        DeviceEventBus bus = new IDeviceEventBus();
        TestValuesDevice device = new TestValuesDevice("id", bus);

        // test setting/getting value directly
        device.setValue(4);
        Assert.assertEquals(4, device.getValue());

        // test setting/getting value over device event bus
        BlockingQueue<DeviceEvent> eventQueue = bus.subscriptionQueue(new ValueChangedFilter());
        DeviceEvent event = new IDeviceEvent(DeviceEventType.VALUE_SET, "id", 5);
        bus.broadcast(event);
        event = eventQueue.take();
        Assert.assertEquals(5, event.getPayload());

    }

}
