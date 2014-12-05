package net.realmproject.dcm.event.filter.devicemessagetype;


import net.realmproject.dcm.messaging.DeviceMessageType;


public class ValueGetFilter extends DeviceMessageTypeFilter {

    public ValueGetFilter() {
        super(DeviceMessageType.VALUE_GET);
    }

}
