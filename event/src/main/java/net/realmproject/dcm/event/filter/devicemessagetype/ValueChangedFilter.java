package net.realmproject.dcm.event.filter.devicemessagetype;


import net.realmproject.dcm.messaging.DeviceMessageType;


public class ValueChangedFilter extends DeviceMessageTypeFilter {

    public ValueChangedFilter() {
        super(DeviceMessageType.VALUE_CHANGED);
    }

}
