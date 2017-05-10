package net.realmproject.dcm.features.ping;


import net.realmproject.dcm.parcel.IParcel;
import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.node.filter.FilterBuilder;
import net.realmproject.dcm.parcel.node.hub.ParcelHub;
import net.realmproject.dcm.parcel.node.identity.Identity;
import net.realmproject.dcm.parcel.node.publisher.ParcelPublisher;
import net.realmproject.dcm.parcel.node.receiver.IParcelConsumer;


public interface Pingable extends Identity, ParcelPublisher {

    default void initPingable(ParcelHub bus) {
        // Respond to Pings
        bus.subscribe(FilterBuilder.start().payload(Ping.class).target(getId()), new IParcelConsumer(getId(), this::onPing));
    }

    default void onPing(Parcel<?> event) {
        Ping ping = (Ping) event.getPayload();
        publish(new IParcel<>(getId(), event.getSourceId(), ping));
    }

}
