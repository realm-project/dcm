package net.realmproject.dcm.messaging.transcoders;


import java.io.Serializable;

import net.realmproject.dcm.messaging.DeviceMessage;
import net.realmproject.dcm.messaging.Transcoder;


public class IIdentityTranscoder implements Transcoder {

    @Override
    public DeviceMessage encode(DeviceMessage message) {
        return message;
    }

    @Override
    public DeviceMessage decode(Serializable message) {
        return (DeviceMessage) message;
    }

}
