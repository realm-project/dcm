package net.realmproject.dcm.accessor.impl;


import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.accessor.DeviceLatencyMonitor;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.util.DCMThreadPool;


public class IDeviceLatencyMonitor extends IDevicePinger implements DeviceLatencyMonitor {

    private static final int DEFAULT_PING_INTERVAL = 5;

    public IDeviceLatencyMonitor(String id, DeviceEventBus bus, String targetId) {
        this(id, bus, targetId, DEFAULT_PING_INTERVAL);
    }

    public IDeviceLatencyMonitor(String id, DeviceEventBus bus, String targetId, int interval) {
        super(id, bus, targetId);
        DCMThreadPool.getPool().scheduleAtFixedRate(this::ping, interval, interval, TimeUnit.SECONDS);
    }

}
