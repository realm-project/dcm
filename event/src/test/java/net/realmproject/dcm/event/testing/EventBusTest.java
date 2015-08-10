package net.realmproject.dcm.event.testing;


import java.util.concurrent.BlockingQueue;

import org.junit.Assert;
import org.junit.Test;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.bus.IDeviceEventBus;


public class EventBusTest {

    // test to make sure a bus is relaying a payload reliably
    @Test
    public void relay() throws InterruptedException {

        DeviceEventBus bus = new IDeviceEventBus();
        BlockingQueue<DeviceEvent> eventQueue = bus.subscriptionQueue();

        bus.accept(new IDeviceEvent(DeviceEventType.MESSAGE, "testid", null, "Hello"));

        DeviceEvent event = eventQueue.take();
        Assert.assertEquals("Hello", event.getPayload());

    }

    // test if the bus is isolating sender from receivers by doing some kind of
    // deep copy of the payload
    @Test
    public void modification() throws InterruptedException {

        DeviceEventBus bus = new IDeviceEventBus();
        BlockingQueue<DeviceEvent> eventQueue = bus.subscriptionQueue();

        StringBuilder sb = new StringBuilder("Hello");
        bus.accept(new IDeviceEvent(DeviceEventType.MESSAGE, "testid", null, sb));
        sb.append(" World!"); // modify payload state after sending

        DeviceEvent event = eventQueue.take();
        Assert.assertEquals("Hello", event.getPayload().toString());
    }

    @Test
    public void filter() throws InterruptedException {

        DeviceEventBus bus = new IDeviceEventBus();
        // only accept messages containing the word "world"
        bus.setFilter(e -> e.getPayload().toString().equals("World"));
        BlockingQueue<DeviceEvent> eventQueue = bus.subscriptionQueue();

        bus.accept(new IDeviceEvent(DeviceEventType.MESSAGE, "testid", null, "Hello"));
        bus.accept(new IDeviceEvent(DeviceEventType.MESSAGE, "testid", null, "World"));

        DeviceEvent event = eventQueue.take();
        Assert.assertEquals("World", event.getPayload().toString());

    }

    @Test
    public void transform() throws InterruptedException {

        DeviceEventBus bus = new IDeviceEventBus();
        // append a "!" to the end of all event payloads
        bus.setTransform(e -> {
            e.setPayload(e.getPayload().toString() + "!");
            return e;
        });
        BlockingQueue<DeviceEvent> eventQueue = bus.subscriptionQueue();

        bus.accept(new IDeviceEvent(DeviceEventType.MESSAGE, "testid", null, "World"));

        DeviceEvent event = eventQueue.take();
        Assert.assertEquals("World!", event.getPayload().toString());

    }

}
