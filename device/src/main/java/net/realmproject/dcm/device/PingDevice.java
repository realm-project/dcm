package net.realmproject.dcm.device;


import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.receiver.DeviceEventReceiver;
import net.realmproject.dcm.features.ping.Pingable;


public class PingDevice implements Pingable {

    private String id;
    private DeviceEventReceiver receiver;

    public PingDevice(String id, DeviceEventBus bus) {
        this.id = id;
        this.receiver = bus;
        initPingable(bus);
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
    public void publish(DeviceEvent event) {
        receiver.accept(event);
    }

}
