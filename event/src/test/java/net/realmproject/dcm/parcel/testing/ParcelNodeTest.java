package net.realmproject.dcm.parcel.testing;


import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import net.realmproject.dcm.parcel.IParcel;
import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.flow.hub.IParcelBridge;
import net.realmproject.dcm.parcel.flow.hub.IParcelHub;
import net.realmproject.dcm.parcel.flow.hub.ParcelHub;
import net.realmproject.dcm.parcel.node.receiver.ParcelReceiverQueue;


public class ParcelNodeTest {

    // test to make sure a bus is relaying a payload reliably
    @Test
    public void relay() throws InterruptedException {

        ParcelHub bus = new IParcelHub();
        ParcelReceiverQueue parcelQueue = new ParcelReceiverQueue();
        bus.subscribe(parcelQueue);

        bus.receive(new IParcel<String>("testid", null, "Hello"));

        Parcel<?> parcel = parcelQueue.poll(5, TimeUnit.SECONDS);
        Assert.assertEquals("Hello", parcel.getPayload());

    }

    // test if the bus is isolating sender from receivers by doing some kind of
    // deep copy of the payload
    @Test
    public void modification() throws InterruptedException {

        ParcelHub bus = new IParcelHub();
        ParcelReceiverQueue parcelQueue = new ParcelReceiverQueue();
        bus.subscribe(parcelQueue);

        StringBuilder sb = new StringBuilder("Hello");
        bus.receive(new IParcel<Serializable>("testid", null, sb));
        sb.append(" World!"); // modify payload state after sending

        Parcel<?> parcel = parcelQueue.poll(5, TimeUnit.SECONDS);
        Assert.assertEquals("Hello", parcel.getPayload().toString());
    }

    @Test
    public void filter() throws InterruptedException {

        ParcelHub bus = new IParcelHub();
        // only accept messages containing the word "world"
        bus.setFilter(e -> e.getPayload().toString().equals("World"));
        ParcelReceiverQueue parcelQueue = new ParcelReceiverQueue();
        bus.subscribe(parcelQueue);

        bus.receive(new IParcel<String>("testid", null, "Hello"));
        bus.receive(new IParcel<String>("testid", null, "World"));

        Parcel<?> parcel = parcelQueue.poll(5, TimeUnit.SECONDS);
        Assert.assertEquals("World", parcel.getPayload().toString());

    }

    @Test
    public void transform() throws InterruptedException {

        ParcelHub bus = new IParcelHub();
        // append a "!" to the end of all parcel payloads
        bus.setTransform(e -> {
        	Parcel<String> p = new IParcel<>();
        	e.derive(p);
            p.setPayload(e.getPayload().toString() + "!");
            return e;
        });
        ParcelReceiverQueue parcelQueue = new ParcelReceiverQueue();
        bus.subscribe(parcelQueue);

        bus.receive(new IParcel<String>("testid", null, "World"));

        Parcel<?> parcel = parcelQueue.poll(5, TimeUnit.SECONDS);
        Assert.assertEquals("World!", parcel.getPayload().toString());

    }

    @Test
    public void bridge() throws InterruptedException {

        ParcelHub bus1 = new IParcelHub();
        ParcelHub bus2 = new IParcelHub();
        new IParcelBridge(bus1, bus2);

        ParcelReceiverQueue parcelQueue = new ParcelReceiverQueue();
        bus2.subscribe(parcelQueue);
        bus1.receive(new IParcel<String>("testid", null, "World"));

        Parcel<?> parcel = parcelQueue.poll(5, TimeUnit.SECONDS);
        Assert.assertEquals("World", parcel.getPayload());

    }

    // make sure busses aren't propagating parcels from other zones
    @Test
    public void zone() throws InterruptedException {

        ParcelHub bus1 = new IParcelHub("zone-1");
        ParcelHub bus2 = new IParcelHub("zone-2");
        new IParcelBridge(bus1, bus2);

        ParcelReceiverQueue parcelQueue = new ParcelReceiverQueue();
        bus2.subscribe(parcelQueue);
        Parcel<?> parcel;
        parcel = new IParcel<String>("testid", null, "Hello");
        parcel.setLocal(true);
        bus1.receive(parcel);
        parcel = new IParcel<String>("testid", null, "World");
        bus1.receive(parcel);

        parcel = parcelQueue.poll(5, TimeUnit.SECONDS);
        Assert.assertEquals("World", parcel.getPayload());

    }

}
