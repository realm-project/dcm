package net.realmproject.dcm.messaging.transcoders;


import java.io.Serializable;

import net.realmproject.dcm.messaging.WireMessage;
import net.realmproject.dcm.messaging.Transcoder;
import net.realmproject.dcm.util.DCMSerialize;


public class IJsonTranscoder implements Transcoder {

    @Override
    public Serializable encode(WireMessage message) {
        // keep class info so the recipient can reconstruct payload property
        return DCMSerialize.serializeWithClassInfo(message);
    }

    @Override
    public WireMessage decode(Serializable message) {
        return DCMSerialize.deserialize((String) message, WireMessage.class);
    }
}
