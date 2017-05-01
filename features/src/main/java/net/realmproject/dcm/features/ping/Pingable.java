package net.realmproject.dcm.features.ping;


import net.realmproject.dcm.parcel.ISerializableParcel;
import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.bus.ParcelHub;
import net.realmproject.dcm.parcel.filter.FilterBuilder;
import net.realmproject.dcm.parcel.identity.Identity;
import net.realmproject.dcm.parcel.publisher.ParcelPublisher;


public interface Pingable extends Identity, ParcelPublisher {

    default void initPingable(ParcelHub bus) {
        // Respond to Pings
        bus.subscribe(FilterBuilder.start().payload(Ping.class).target(getId()), this::onPing);
    }

    default void onPing(Parcel<?> event) {
        Ping ping = (Ping) event.getPayload();
        publish(new ISerializableParcel<>(event.getSourceId(), null, ping));
    }

}
