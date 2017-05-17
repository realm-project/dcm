package net.realmproject.dcm.parcel.core.transform;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.link.ParcelLink;

public interface ParcelTransformLink extends ParcelTransformer, ParcelLink {

	@Override
	default void receive(Parcel<?> parcel) {
		send(transform(parcel));
	}
	
}
