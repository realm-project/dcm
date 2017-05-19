package net.realmproject.dcm.stock.ping;


import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.parcel.core.flow.hub.ParcelHub;
import net.realmproject.dcm.util.DCMSettings;
import net.realmproject.dcm.util.DCMThreadPool;


public class IDeviceLatencyMonitor extends IDevicePinger implements DeviceLatencyMonitor {

    private static final int DEFAULT_PING_INTERVAL = 5;

    public IDeviceLatencyMonitor(String id, ParcelHub bus, String targetId) {
        this(id, bus, targetId, DEFAULT_PING_INTERVAL);
    }

    public IDeviceLatencyMonitor(String id, ParcelHub bus, String targetId, int interval) {
        super(id, bus, targetId);
        DCMThreadPool.getScheduledPool().scheduleAtFixedRate(this::ping, DCMSettings.STARTUP_DELAY, interval, TimeUnit.SECONDS);
    }

}
