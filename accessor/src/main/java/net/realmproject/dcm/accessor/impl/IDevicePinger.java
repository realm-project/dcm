package net.realmproject.dcm.accessor.impl;


import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.function.Predicate;

import net.realmproject.dcm.accessor.DevicePinger;
import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.composite.BooleanAndFilter;
import net.realmproject.dcm.util.DCMThreadPool;


public class IDevicePinger implements DevicePinger {

    private class PingResponse {

        public boolean responded = false;
    }

    private String id;
    private DeviceEventBus bus;

    public IDevicePinger(String id, DeviceEventBus bus) {
        this.id = id;
        this.bus = bus;
    }

    @Override
    public String getDeviceId() {
        return id;
    }

    @Override
    public void ping() {
        ping(UUID.randomUUID());
    }

    private UUID ping(UUID uuid) {
        bus.broadcast(new IDeviceEvent(DeviceEventType.PING, id, uuid));
        return uuid;
    }

    @Override
    public Future<Long> pingAndWait() {
        return pingAndWait(0);
    }

    @Override
    public Future<Long> pingAndWait(long timeout) {

        return DCMThreadPool.getPool().submit(() -> {

            /* Our very own monitor */
            PingResponse response = new PingResponse();
            long t1, t2;

            t1 = new Date().getTime();

            /* grab the monitor */
            synchronized (response) {

                UUID uuid = UUID.randomUUID();
                Predicate<DeviceEvent> filter = new BooleanAndFilter();

                bus.subscribe(event -> {
                    /* grab the monitor to make sure ping is waiting */
                    synchronized (response) {
                        response.responded = true;
                        this.notifyAll();
                    }

                }, filter);

                ping(uuid);
                /* release the monitor and wait to be signaled */
                response.wait(timeout);
                if (response.responded) {
                    t2 = new Date().getTime();
                    return t2 - t1;
                } else {
                    return null;
                }

            }

        });

    }

}
