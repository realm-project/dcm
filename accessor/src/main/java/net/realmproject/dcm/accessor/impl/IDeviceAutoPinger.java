package net.realmproject.dcm.accessor.impl;


import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.Ping;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.Filters;
import net.realmproject.dcm.util.DCMThreadPool;


public class IDeviceAutoPinger extends IDevicePinger {

    private Ping lastPing;

    public IDeviceAutoPinger(String id, DeviceEventBus bus) {
        super(id, bus);

        DCMThreadPool.getPool().scheduleAtFixedRate(this::ping, 2, 1, TimeUnit.SECONDS);
        bus.subscribe(this::onPong, Filters.filter().id(id).pongEvents().booleanAnd());

    }

    private void onPong(DeviceEvent event) {
        Ping ping = (Ping) event.getValue();
        if (lastPing == null || lastPing.startedBefore(ping)) {
            lastPing = ping;
        }
    }

    public long getPingTime() {
        if (lastPing == null) { return -1l; }
        return lastPing.getPingTime();
    }

}
