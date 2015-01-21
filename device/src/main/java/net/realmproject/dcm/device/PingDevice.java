package net.realmproject.dcm.device;


import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.composite.BooleanAndFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.PingFilter;
import net.realmproject.dcm.event.filter.deviceid.DeviceIDWhitelistFilter;


/**
 * A device which responds to pings with pongs. This kind of device has no value
 * 
 * @author NAS
 *
 */

public class PingDevice extends Device {

    public PingDevice(String id, DeviceEventBus bus) {
        super(id, bus);
        Predicate<DeviceEvent> filter = new BooleanAndFilter(new PingFilter(), new DeviceIDWhitelistFilter(id));
        bus.subscribe(event -> send(new IDeviceEvent(DeviceEventType.PONG, id, event.getValue())), filter);
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(Object val) {}

}
