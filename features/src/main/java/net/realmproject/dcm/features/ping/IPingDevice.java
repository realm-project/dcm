package net.realmproject.dcm.features.ping;


import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.node.hub.ParcelHub;
import net.realmproject.dcm.parcel.node.receiver.ParcelReceiver;


public class IPingDevice implements Pingable {

    private String id;
    private ParcelReceiver receiver;

    public IPingDevice(String id, ParcelHub bus) {
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
    public void publish(Parcel event) {
        receiver.receive(event);
    }

}
