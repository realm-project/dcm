package net.realmproject.dcm.network.transcoder;


import java.io.Serializable;

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
