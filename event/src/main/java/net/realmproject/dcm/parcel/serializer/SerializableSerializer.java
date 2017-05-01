package net.realmproject.dcm.parcel.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;



public class SerializableSerializer<S> implements Serializer<S> {

	@Override
	public Serializable serialize(S input) throws SerializerException {
		
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(input);

            return baos.toByteArray();

        }
        catch (IOException e) {
            throw new SerializerException(e);
        }

	}

	@Override
	public S deserialize(Serializable input) throws SerializerException {
		byte[] bytes = (byte[]) input;
        try {
        	ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        	ObjectInputStream ois = new ObjectInputStream(bais);
        	return (S) ois.readObject();
        } catch (Exception e) {
        	throw new SerializerException(e);
        }
	}

}
