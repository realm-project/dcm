package net.realmproject.dcm.stock.ping;



import net.realmproject.dcm.features.ping.Ping;
import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.flow.hub.ParcelHub;
import net.realmproject.dcm.parcel.impl.flow.filter.FilterBuilder;
import net.realmproject.dcm.parcel.impl.flow.filter.IParcelFilterLink;
import net.realmproject.dcm.parcel.impl.parcel.IParcel;
import net.realmproject.dcm.parcel.impl.receiver.IParcelConsumer;


public class IDevicePinger implements DevicePinger {

    private String id, targetId;
    private ParcelReceiver receiver;
    private Ping lastPing;

    public IDevicePinger(String id, ParcelHub bus, String targetId) {
        this.id = id;
        this.receiver = bus;
        this.targetId = targetId;
        bus
        	.link(new IParcelFilterLink(FilterBuilder.start().source(targetId).payload(Ping.class)))
        	.link(new IParcelConsumer(id, this::onPong));
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
        receiver.receive(new IParcel<>()
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

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String deviceId) {
        this.targetId = deviceId;
    }

}
