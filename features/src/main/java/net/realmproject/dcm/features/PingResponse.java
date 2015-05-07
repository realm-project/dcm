package net.realmproject.dcm.features;

import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.composite.BooleanAndFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.PingFilter;
import net.realmproject.dcm.event.filter.deviceid.DeviceIDWhitelistFilter;

public interface PingResponse extends Publishing {

	default void initPingResponse(DeviceEventBus bus) {
        // Respond to Pings
        Predicate<DeviceEvent> filter = new BooleanAndFilter(new PingFilter(), new DeviceIDWhitelistFilter(getId()));
        bus.subscribe(this::onPing, filter);
	}
	
    default void onPing(DeviceEvent event) {
    	publish(event.getValue(), DeviceEventType.PONG);
    }
	
}
