package net.realmproject.dcm.parcel.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;



public class SerializableParcelSerializer<S extends Serializable> implements ParcelSerializer<S> {

	@Override
	public Serializable serialize(S input) throws ParcelSerializerException {
		
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(input);

            return baos.toByteArray();

        }
        catch (IOException e) {
            throw new ParcelSerializerException(e);
        }

	}

	@Override
	public S deserialize(Serializable input) throws ParcelSerializerException {
		byte[] bytes = (byte[]) input;
        try {
        	ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        	ObjectInputStream ois = new ObjectInputStream(bais);
        	return (S) ois.readObject();
        } catch (Exception e) {
        	throw new ParcelSerializerException(e);
        }
	}

}
