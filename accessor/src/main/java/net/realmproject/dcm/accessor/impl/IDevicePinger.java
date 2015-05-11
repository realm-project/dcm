package net.realmproject.dcm.accessor.impl;


import net.realmproject.dcm.accessor.DevicePinger;
import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.Ping;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.Filters;


public class IDevicePinger implements DevicePinger {

    private String id;
    private DeviceEventBus bus;
    private Ping lastPing;

    public IDevicePinger(String id, DeviceEventBus bus) {
        this.id = id;
        this.bus = bus;
        bus.subscribe(Filters.id(id).and(Filters.pongEvents()), this::onPong);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void ping() {
        ping(new Ping());
    }

    private Ping ping(Ping thePing) {
        bus.broadcast(new IDeviceEvent(DeviceEventType.PING, id, thePing));
        return thePing;
    }

    private void onPong(DeviceEvent event) {
        Ping ping = (Ping) event.getValue();
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
