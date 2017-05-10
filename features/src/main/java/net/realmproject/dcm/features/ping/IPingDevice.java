package net.realmproject.dcm.features.ping;


import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.hub.ParcelHub;


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
    public void send(Parcel event) {
        receiver.receive(event);
    }

}
