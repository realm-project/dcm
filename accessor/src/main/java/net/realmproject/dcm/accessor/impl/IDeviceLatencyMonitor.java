package net.realmproject.dcm.accessor.impl;


import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.accessor.DeviceLatencyMonitor;
import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.Ping;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.FilterBuilder;
import net.realmproject.dcm.util.DCMThreadPool;


public class IDeviceLatencyMonitor extends IDevicePinger implements DeviceLatencyMonitor {

    private Ping lastPing;

    private static final int DEFAULT_PING_INTERVAL = 5;

    public IDeviceLatencyMonitor(String id, DeviceEventBus bus) {
        this(id, bus, DEFAULT_PING_INTERVAL);
    }

    public IDeviceLatencyMonitor(String id, DeviceEventBus bus, int interval) {
        super(id, bus);
        DCMThreadPool.getPool().scheduleAtFixedRate(this::ping, interval, interval, TimeUnit.SECONDS);
        bus.subscribe(this::onPong, FilterBuilder.filter().id(id).pongEvents().requireAll());
    }

    private void onPong(DeviceEvent event) {
        Ping ping = (Ping) event.getValue();
        if (lastPing == null || lastPing.startedBefore(ping)) {
            lastPing = ping;
        }
    }

    public long getLatency() {
        if (lastPing == null) { return -1l; }
        return lastPing.getPingTime();
    }

}
