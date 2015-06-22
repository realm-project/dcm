package net.realmproject.dcm.messaging;


import java.io.Serializable;


/**
 * Interface for transcoding {@link DeviceMessage}s into a different format for
 * sending/receiving over a messaging implementation
 * 
 * @author NAS
 *
 */
public interface Transcoder {

    public Serializable encode(DeviceMessage message);

    public DeviceMessage decode(Serializable message);

}
