package net.realmproject.dcm.messaging;


import java.io.Serializable;


public interface Transcoder {

    public Serializable encode(DeviceMessage message);

    public DeviceMessage decode(Serializable message);

}
