package net.realmproject.dcm.messaging.impl;


import java.io.Serializable;

import net.realmproject.dcm.messaging.DeviceMessage;
import net.realmproject.dcm.messaging.DeviceMessageTranscoder;


public class IIdentityDeviceMessageTranscoder implements DeviceMessageTranscoder {

    @Override
    public DeviceMessage encode(DeviceMessage message) {
        return message;
    }

    @Override
    public DeviceMessage decode(Serializable message) {
        return (DeviceMessage) message;
    }

}
