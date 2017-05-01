package net.realmproject.dcm.parcel.serializer;

public class SerializerException extends RuntimeException {
	
	public SerializerException(Throwable cause) {
		super(cause);
	}
	
    public SerializerException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
