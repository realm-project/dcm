package net.realmproject.dcm.network.transcoder;


import java.io.Serializable;

import net.realmproject.dcm.network.WireMessage;


/**
 * Interface for transcoding {@link WireMessage}s into a different format for
 * sending/receiving over a messaging implementation
 * 
 * @author NAS
 *
 */
public interface Transcoder {

    public Serializable encode(WireMessage message);

    public WireMessage decode(Serializable message);

}
