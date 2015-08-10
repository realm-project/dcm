package net.realmproject.dcm.stock.ping;


import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.FilterBuilder;
import net.realmproject.dcm.event.receiver.DeviceEventReceiver;
import net.realmproject.dcm.features.ping.Ping;


public class IDevicePinger implements DevicePinger {

    private String id, targetId;
    private DeviceEventReceiver receiver;
    private Ping lastPing;

    public IDevicePinger(String id, DeviceEventBus bus, String targetId) {
        this.id = id;
        this.receiver = bus;
        this.targetId = targetId;
        bus.subscribe(FilterBuilder.start().source(targetId).payload(Ping.class), this::onPong);
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
        receiver.accept(new IDeviceEvent().type(DeviceEventType.MESSAGE)
                .sourceId(getId())
                .targetId(getTargetId())
                .payload(thePing));
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

    @Override
    public String getTargetId() {
        return targetId;
    }

    @Override
    public void setTargetId(String deviceId) {
        this.targetId = deviceId;
    }

}
