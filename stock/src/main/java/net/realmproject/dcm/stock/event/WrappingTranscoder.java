package net.realmproject.dcm.stock.event;


import java.io.Serializable;

import net.realmproject.dcm.network.WireMessage;
import net.realmproject.dcm.network.transcoder.Transcoder;
import net.realmproject.dcm.parcel.IParcel;


public class WrappingTranscoder implements Transcoder<WireMessage, Serializable> {

    @Override
    public Serializable encode(WireMessage message) {
        return message.getParcel().getPayload();
    }

    @Override
    public WireMessage decode(Serializable message) {
        return new WireMessage(new IParcel(null, null, message));
    }

}
