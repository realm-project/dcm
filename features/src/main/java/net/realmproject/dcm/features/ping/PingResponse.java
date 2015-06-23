package net.realmproject.dcm.features.ping;


import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.DeviceIDFilter;
import net.realmproject.dcm.features.Publishing;


public interface PingResponse extends Publishing {

    default void initPingResponse(DeviceEventBus bus) {
        // Respond to Pings
        Predicate<DeviceEvent> filter = new PingFilter().and(new DeviceIDFilter(getId()));
        bus.subscribe(filter, this::onPing);
    }

    default void onPing(DeviceEvent event) {
        PingMessage ping = (PingMessage) event.getValue();
        PongMessage pong = new PongMessage(ping);
        publish(pong, DeviceEventType.MESSAGE);
    }

}
