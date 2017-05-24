package net.realmproject.dcm.stock.examples.ide;

import net.realmproject.dcm.parcel.core.ParcelNode;
import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.parcel.core.metadata.ParcelNodeType;

public class NodeUtil {

	public static ParcelNodeType getType(Class<? extends ParcelNode> clazz) {
		if (clazz.isAnnotationPresent(ParcelMetadata.class)) {
			ParcelMetadata md = clazz.getAnnotation(ParcelMetadata.class);
			return md.type();
		}
		return ParcelNodeType.OTHER;
	}
	
	public static String getName(Class<? extends ParcelNode> clazz) {
		if (clazz.isAnnotationPresent(ParcelMetadata.class)) {
			ParcelMetadata md = clazz.getAnnotation(ParcelMetadata.class);
			return md.name();
		}
		return clazz.getSimpleName();
	}
	
}
