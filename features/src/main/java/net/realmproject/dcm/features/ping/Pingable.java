package net.realmproject.dcm.features.ping;


import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.DeviceIDFilter;
import net.realmproject.dcm.event.filter.PayloadClassFilter;
import net.realmproject.dcm.features.Publishing;


public interface Pingable extends Publishing {

    default void initPingable(DeviceEventBus bus, String sourceId) {
        // Respond to Pings
        Predicate<DeviceEvent> filter = new PayloadClassFilter(Ping.class).and(new DeviceIDFilter(sourceId));
        bus.subscribe(filter, this::onPing);
    }

    default void onPing(DeviceEvent event) {
        Ping ping = (Ping) event.getPayload();
        publish(ping, DeviceEventType.MESSAGE);
    }

}
