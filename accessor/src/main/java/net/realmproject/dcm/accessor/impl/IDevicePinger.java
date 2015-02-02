package net.realmproject.dcm.accessor.impl;


import net.realmproject.dcm.accessor.DevicePinger;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.Ping;
import net.realmproject.dcm.event.bus.DeviceEventBus;


public class IDevicePinger implements DevicePinger {

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
        ping(new Ping());
    }

    private Ping ping(Ping thePing) {
        bus.broadcast(new IDeviceEvent(DeviceEventType.PING, id, thePing));
        return thePing;
    }

}
