package net.realmproject.dcm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

//Kinda Unique ID -- not cryptographically secure or anything
public class DCMUtil {

	private static Random r = new Random();

	public static String generateId() {
		return new UUID(r.nextLong(), r.nextLong()).toString();
	}

	public static byte[] serialize(Object input) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(input);
		return baos.toByteArray();

	}

	public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {

		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bais);
		return ois.readObject();

	}

}
