package net.realmproject.dcm.network.transcoder;


import java.io.Serializable;


/**
 * Interface for transcoding data structires into a different format. This is
 * used to adapt to different external wire formats.
 * 
 * @author NAS
 *
 */
public interface Transcoder<T1, T2 extends Serializable> {

    public T2 encode(T1 message);

    public T1 decode(T2 message) throws TranscoderException;

}
