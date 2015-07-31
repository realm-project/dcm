package net.realmproject.dcm.device.test.ping;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import org.junit.Test;

import junit.framework.Assert;
import net.realmproject.dcm.device.PingDevice;
import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.bus.IDeviceEventBus;
import net.realmproject.dcm.event.filter.FilterBuilder;
import net.realmproject.dcm.features.ping.Ping;


public class PingTest {

    @Test
    public void pingTest() throws InterruptedException {

        DeviceEventBus bus = new IDeviceEventBus();
        Predicate<DeviceEvent> pingerFilter = FilterBuilder.start()
                .eventMessage()
                .payload(Ping.class)
                .source("pinger")
                .target("ponger");
        BlockingQueue<DeviceEvent> events = bus.subscriptionQueue(pingerFilter);

        // create the ping responder
        PingDevice device = new PingDevice("ponger", bus);

        // send the ping
        Ping ping = new Ping();
        bus.accept(new IDeviceEvent(DeviceEventType.MESSAGE, "pinger", "ponger", ping));

        // check for response
        DeviceEvent response = events.poll(5, TimeUnit.SECONDS);
        Ping pong = response.getPayload();

        Assert.assertEquals(ping, pong);

    }

}
