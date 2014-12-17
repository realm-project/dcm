package net.realmproject.dcm.device;


import java.io.Serializable;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.composite.BooleanAndFilter;
import net.realmproject.dcm.event.filter.deviceid.DeviceIDWhitelistFilter;
import net.realmproject.dcm.event.filter.devicemessagetype.ValueGetFilter;
import net.realmproject.dcm.event.filter.devicemessagetype.ValueSetFilter;
import net.realmproject.dcm.messaging.DeviceMessageType;


public abstract class SimpleDevice extends Device {

    public SimpleDevice(String id, DeviceEventBus bus) {
        super(id, bus);
        bus.subscribe(this::onSet, new BooleanAndFilter(new DeviceIDWhitelistFilter(getId()), new ValueSetFilter()));
        bus.subscribe(this::onGet, new BooleanAndFilter(new DeviceIDWhitelistFilter(getId()), new ValueGetFilter()));
    }

    private void onSet(DeviceEvent e) {
        setValue(e.getValue());
    }

    private void onGet(DeviceEvent e) {
        send(new IDeviceEvent(DeviceMessageType.VALUE_CHANGED, getId(), (Serializable) getValue()));
    }

}
