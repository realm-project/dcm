package net.realmproject.dcm.device;


import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.features.ping.Pingable;


public class PingDevice implements Pingable {

    private String id;
    private DeviceEventBus bus;
    private long sentMessages = 0l;

    public PingDevice(String id, DeviceEventBus bus) {
        this.id = id;
        this.bus = bus;
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
    public boolean send(DeviceEvent event) {
        boolean success = bus.broadcast(event);
        if (success) {
            sentMessages++;
        }
        return success;
    }

    @Override
    public boolean isSending() {
        return true;
    }

    @Override
    public void setSending(boolean sending) {}

    @Override
    public long sendCount() {
        return sentMessages;
    }

}
