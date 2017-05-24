package net.realmproject.dcm.parcel.core.metadata;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)		
public @interface ParcelMetadata {

	ParcelNodeType type();
	String name();
	
}
