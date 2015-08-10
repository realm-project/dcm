package net.realmproject.dcm.features.ping;


import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.FilterBuilder;
import net.realmproject.dcm.event.identity.Identity;
import net.realmproject.dcm.event.publisher.DeviceEventPublisher;


public interface Pingable extends Identity, DeviceEventPublisher {

    default void initPingable(DeviceEventBus bus) {
        // Respond to Pings
        bus.subscribe(FilterBuilder.start().payload(Ping.class).target(getId()), this::onPing);
    }

    default void onPing(DeviceEvent event) {
        Ping ping = (Ping) event.getPayload();
        publish(DeviceEventType.MESSAGE, event.getSourceId(), ping);
    }

}
