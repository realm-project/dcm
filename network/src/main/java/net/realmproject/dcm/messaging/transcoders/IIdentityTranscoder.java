package net.realmproject.dcm.messaging.transcoders;


import java.io.Serializable;

import net.realmproject.dcm.messaging.WireMessage;
import net.realmproject.dcm.messaging.Transcoder;


public class IIdentityTranscoder implements Transcoder {

    @Override
    public WireMessage encode(WireMessage message) {
        return message;
    }

    @Override
    public WireMessage decode(Serializable message) {
        return (WireMessage) message;
    }

}
