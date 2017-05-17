package net.realmproject.dcm.parcel.core.filter;


import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.link.ParcelLink;

public interface ParcelFilterLink extends ParcelLink, ParcelFilterer {

	@Override
	default void receive(Parcel<?> parcel) {
		if (!filter(parcel)) { return; }
		send(parcel);
	}
	
}
