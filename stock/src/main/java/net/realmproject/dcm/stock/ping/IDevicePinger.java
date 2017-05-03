package net.realmproject.dcm.stock.ping;



import net.realmproject.dcm.features.ping.Ping;
import net.realmproject.dcm.parcel.IParcel;
import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.bus.ParcelHub;
import net.realmproject.dcm.parcel.filter.FilterBuilder;
import net.realmproject.dcm.parcel.receiver.IParcelConsumer;
import net.realmproject.dcm.parcel.receiver.ParcelReceiver;


public class IDevicePinger implements DevicePinger {

    private String id, targetId;
    private ParcelReceiver receiver;
    private Ping lastPing;

    public IDevicePinger(String id, ParcelHub bus, String targetId) {
        this.id = id;
        this.receiver = bus;
        this.targetId = targetId;
        bus.subscribe(FilterBuilder.start().source(targetId).payload(Ping.class), new IParcelConsumer(id, this::onPong));
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
        receiver.accept(new IParcel<>()
                .sourceId(getId())
                .targetId(getTargetId())
                .payload(thePing));
        return thePing;
    }

    private void onPong(Parcel<?> event) {
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
