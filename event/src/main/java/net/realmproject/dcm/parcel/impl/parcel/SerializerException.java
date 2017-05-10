package net.realmproject.dcm.parcel.impl.parcel;

public class SerializerException extends RuntimeException {
	
	public SerializerException(Throwable cause) {
		super(cause);
	}
	
    public SerializerException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
