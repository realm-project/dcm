package net.realmproject.dcm.features.ping;


import net.realmproject.dcm.parcel.core.Identity;
import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelSender;
import net.realmproject.dcm.parcel.core.hub.ParcelHub;
import net.realmproject.dcm.parcel.impl.filter.FilterBuilder;
import net.realmproject.dcm.parcel.impl.parcel.IParcel;
import net.realmproject.dcm.parcel.impl.receiver.IParcelConsumer;


public interface Pingable extends Identity, ParcelSender {

    default void initPingable(ParcelHub bus) {
        // Respond to Pings
        bus.subscribe(FilterBuilder.start().payload(Ping.class).target(getId()), new IParcelConsumer(getId(), this::onPing));
    }

    default void onPing(Parcel<?> event) {
        Ping ping = (Ping) event.getPayload();
        send(new IParcel<>(getId(), event.getSourceId(), ping));
    }

}
