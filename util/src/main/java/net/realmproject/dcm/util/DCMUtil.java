package net.realmproject.dcm.util;

import java.util.Random;
import java.util.UUID;

//Kinda Unique ID -- not cryptographically secure or anything
public class DCMUtil {

	private static Random r = new Random();
	

	public static String generateId() {
		return new UUID(r.nextLong(), r.nextLong()).toString();
	}
	
}
