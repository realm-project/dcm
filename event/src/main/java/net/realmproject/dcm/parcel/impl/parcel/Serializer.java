package net.realmproject.dcm.parcel.impl.parcel;

import java.io.Serializable;

public interface Serializer<T> extends Serializable {

	Serializable serialize(T input) throws SerializerException;
	
	T deserialize(Serializable input) throws SerializerException;
	
	default T copy(T input) throws SerializerException {
		return deserialize(serialize(input));
	}
	
}
