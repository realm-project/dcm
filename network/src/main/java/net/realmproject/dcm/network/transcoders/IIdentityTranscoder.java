package net.realmproject.dcm.network.transcoders;


import java.io.Serializable;

import net.realmproject.dcm.network.Transcoder;
import net.realmproject.dcm.network.WireMessage;


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
