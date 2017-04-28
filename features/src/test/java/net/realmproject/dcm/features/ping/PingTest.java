package net.realmproject.dcm.features.ping;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import org.junit.Test;

import junit.framework.Assert;
import net.realmproject.dcm.features.ping.IPingDevice;
import net.realmproject.dcm.features.ping.Ping;
import net.realmproject.dcm.parcel.IParcel;
import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.bus.IParcelHub;
import net.realmproject.dcm.parcel.bus.ParcelHub;
import net.realmproject.dcm.parcel.filter.FilterBuilder;


public class PingTest {

    @Test
    public void pingTest() throws InterruptedException {

    	ParcelHub bus = new IParcelHub();
        Predicate<Parcel> pingerFilter = FilterBuilder.start()
                .payload(Ping.class)
                .source("pinger")
                .target("ponger");
        BlockingQueue<Parcel> events = bus.subscriptionQueue(pingerFilter);

        // create the ping responder
        IPingDevice device = new IPingDevice("ponger", bus);

        // send the ping
        Ping ping = new Ping();
        bus.accept(new IParcel("pinger", "ponger", ping));

        // check for response
        Parcel response = events.poll(5, TimeUnit.SECONDS);
        Ping pong = response.getPayload();

        Assert.assertEquals(ping, pong);

    }

}
