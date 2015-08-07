package net.realmproject.dcm.stock.event;


import net.realmproject.dcm.network.WireMessage;
import net.realmproject.dcm.network.transcoder.Transcoder;
import net.realmproject.dcm.util.DCMSerialize;


public class IJsonTranscoder implements Transcoder<WireMessage, String> {

    @Override
    public String encode(WireMessage message) {
        // keep class info so the recipient can reconstruct payload property
        return DCMSerialize.serializeWithClassInfo(message);
    }

    @Override
    public WireMessage decode(String message) {
        return DCMSerialize.deserialize(message, WireMessage.class);
    }
}
