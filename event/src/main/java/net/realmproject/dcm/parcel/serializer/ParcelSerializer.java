package net.realmproject.dcm.parcel.serializer;

import java.io.Serializable;

public interface ParcelSerializer<T> extends Serializable {

	Serializable serialize(T input) throws ParcelSerializerException;
	
	T deserialize(Serializable input) throws ParcelSerializerException;
	
	default T copy(T input) throws ParcelSerializerException {
		return deserialize(serialize(input));
	}
	
}
