package net.realmproject.dcm.accessor.impl;


import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.Ping;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.composite.BooleanAndFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.PongFilter;
import net.realmproject.dcm.event.filter.deviceid.DeviceIDWhitelistFilter;
import net.realmproject.dcm.util.DCMThreadPool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class IDeviceAutoPinger extends IDevicePinger {

    private Log log = LogFactory.getLog(getClass());

    private Ping lastPing;

    public IDeviceAutoPinger(String id, DeviceEventBus bus) {
        super(id, bus);

        DCMThreadPool.getPool().scheduleAtFixedRate(this::ping, 2, 1, TimeUnit.SECONDS);

        Predicate<DeviceEvent> idFilter = new DeviceIDWhitelistFilter(id);
        Predicate<DeviceEvent> filter = new BooleanAndFilter(idFilter, new PongFilter());

        bus.subscribe(this::onPong, filter);

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
