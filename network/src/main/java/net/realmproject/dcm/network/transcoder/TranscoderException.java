package net.realmproject.dcm.network.transcoder;


public class TranscoderException extends RuntimeException {

    public TranscoderException(Throwable cause) {
        super(cause);
    }

    public TranscoderException(String message, Throwable cause) {
        super(message, cause);
    }

}
