package net.realmproject.dcm.parcel.impl.parcel;

import java.io.IOException;
import java.io.Serializable;

import net.realmproject.dcm.util.DCMUtil;



public class SerializableSerializer<S> implements Serializer<S> {

	@Override
	public Serializable serialize(S input) throws SerializerException {
		
        try {
        	return DCMUtil.serialize(input);
        }
        catch (IOException e) {
            throw new SerializerException(e);
        }

	}

	@Override
	public S deserialize(Serializable input) throws SerializerException {
		byte[] bytes = (byte[]) input;
			
        try {
        	return (S) DCMUtil.deserialize(bytes);
        } catch (Exception e) {
        	throw new SerializerException(e);
        }
	}

}
