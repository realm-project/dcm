package net.realmproject.dcm.features.ping;


import net.realmproject.dcm.parcel.core.Identity;
import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelSender;
import net.realmproject.dcm.parcel.core.flow.hub.ParcelHub;
import net.realmproject.dcm.parcel.impl.flow.filter.FilterBuilder;
import net.realmproject.dcm.parcel.impl.flow.filter.IParcelFilterLink;
import net.realmproject.dcm.parcel.impl.parcel.IParcel;
import net.realmproject.dcm.parcel.impl.receiver.IParcelConsumer;


public interface Pingable extends Identity, ParcelSender {

    default void initPingable(ParcelHub bus) {
        // Respond to Pings
        bus
        	.link(new IParcelFilterLink(FilterBuilder.start().payload(Ping.class).target(getId())))
        	.link(new IParcelConsumer(getId(), this::onPing));
    }

    default void onPing(Parcel<?> event) {
        Ping ping = (Ping) event.getPayload();
        send(new IParcel<>(getId(), event.getSourceId(), ping));
    }

}
