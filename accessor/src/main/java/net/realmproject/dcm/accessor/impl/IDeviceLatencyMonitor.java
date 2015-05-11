package net.realmproject.dcm.accessor.impl;


import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.accessor.DeviceLatencyMonitor;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.util.DCMThreadPool;


public class IDeviceLatencyMonitor extends IDevicePinger implements DeviceLatencyMonitor {

    private static final int DEFAULT_PING_INTERVAL = 5;

    public IDeviceLatencyMonitor(String id, DeviceEventBus bus) {
        this(id, bus, DEFAULT_PING_INTERVAL);
    }

    public IDeviceLatencyMonitor(String id, DeviceEventBus bus, int interval) {
        super(id, bus);
        DCMThreadPool.getPool().scheduleAtFixedRate(this::ping, interval, interval, TimeUnit.SECONDS);
    }

}
