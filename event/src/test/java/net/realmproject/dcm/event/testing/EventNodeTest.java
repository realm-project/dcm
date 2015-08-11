package net.realmproject.dcm.event.testing;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.bus.IDeviceEventBridge;
import net.realmproject.dcm.event.bus.IDeviceEventBus;


public class EventNodeTest {

    // test to make sure a bus is relaying a payload reliably
    @Test
    public void relay() throws InterruptedException {

        DeviceEventBus bus = new IDeviceEventBus();
        BlockingQueue<DeviceEvent> eventQueue = bus.subscriptionQueue();

        bus.accept(new IDeviceEvent(DeviceEventType.MESSAGE, "testid", null, "Hello"));

        DeviceEvent event = eventQueue.poll(5, TimeUnit.SECONDS);
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

        DeviceEvent event = eventQueue.poll(5, TimeUnit.SECONDS);
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

        DeviceEvent event = eventQueue.poll(5, TimeUnit.SECONDS);
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

        DeviceEvent event = eventQueue.poll(5, TimeUnit.SECONDS);
        Assert.assertEquals("World!", event.getPayload().toString());

    }

    @Test
    public void bridge() throws InterruptedException {

        DeviceEventBus bus1 = new IDeviceEventBus();
        DeviceEventBus bus2 = new IDeviceEventBus();
        new IDeviceEventBridge(bus1, bus2);

        BlockingQueue<DeviceEvent> eventQueue = bus2.subscriptionQueue();
        bus1.accept(new IDeviceEvent(DeviceEventType.MESSAGE, "testid", null, "World"));

        DeviceEvent event = eventQueue.poll(5, TimeUnit.SECONDS);
        Assert.assertEquals("World", event.getPayload());

    }

    // make sure busses aren't propagating events from other zones
    @Test
    public void zone() throws InterruptedException {

        DeviceEventBus bus1 = new IDeviceEventBus("zone-1");
        DeviceEventBus bus2 = new IDeviceEventBus("zone-2");
        new IDeviceEventBridge(bus1, bus2);

        BlockingQueue<DeviceEvent> eventQueue = bus2.subscriptionQueue();
        DeviceEvent event;
        event = new IDeviceEvent(DeviceEventType.MESSAGE, "testid", null, "Hello");
        event.setPrivate(true);
        bus1.accept(event);
        event = new IDeviceEvent(DeviceEventType.MESSAGE, "testid", null, "World");
        bus1.accept(event);

        event = eventQueue.poll(5, TimeUnit.SECONDS);
        Assert.assertEquals("World", event.getPayload());

    }

}
