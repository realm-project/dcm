package net.realmproject.dcm.accessor.impl;


import net.realmproject.dcm.accessor.DevicePinger;
import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.Filters;
import net.realmproject.dcm.event.filter.PayloadClassFilter;
import net.realmproject.dcm.features.ping.Ping;


public class IDevicePinger implements DevicePinger {

    private String id;
    private DeviceEventBus bus;
    private Ping lastPing;

    public IDevicePinger(String id, DeviceEventBus bus, String targetId) {
        this.id = id;
        this.bus = bus;
        bus.subscribe(Filters.id(targetId).and(new PayloadClassFilter(Ping.class)), this::onPong);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void ping() {
        ping(new Ping());
    }

    private Ping ping(Ping thePing) {
        bus.broadcast(new IDeviceEvent(DeviceEventType.MESSAGE, id, thePing));
        return thePing;
    }

    private void onPong(DeviceEvent event) {
        Ping ping = (Ping) event.getPayload();
        ping.completed();
        if (lastPing == null || lastPing.startedBefore(ping)) {
            lastPing = ping;
        }
    }

    public long getLatency() {
        if (lastPing == null) { return -1l; }
        return lastPing.getPingTime();
    }

}
