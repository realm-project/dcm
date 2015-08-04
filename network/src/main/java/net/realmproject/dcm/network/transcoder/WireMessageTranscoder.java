package net.realmproject.dcm.network.transcoder;

import java.io.Serializable;

import net.realmproject.dcm.network.WireMessage;

public interface WireMessageTranscoder extends Transcoder<WireMessage, Serializable>{

    /**
     * Retrieve the {@link WireMessage} {@link Transcoder}. This is the component used to adapt to other wire formats by de/encoding {@link WireMessage}s
     * @return the current transcoder
     */
    Transcoder<WireMessage, Serializable> getTranscoder();

    /**
     * Sets the {@link WireMessage} {@link Transcoder}. This is the component used to adapt to other wire formats by de/encoding {@link WireMessage}s
     * @param transcoder
     */
    void setTranscoder(Transcoder<WireMessage, Serializable> transcoder);

    default Serializable encode(WireMessage message) {
    	return getTranscoder().encode(message);
    }
    
    default WireMessage decode(Serializable serializable) {
    	return getTranscoder().decode(serializable);
    }
    
}
