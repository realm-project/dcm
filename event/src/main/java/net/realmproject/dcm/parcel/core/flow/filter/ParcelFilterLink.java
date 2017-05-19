package net.realmproject.dcm.parcel.core.flow.filter;


import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.flow.link.ParcelLink;

public interface ParcelFilterLink extends ParcelLink, ParcelFilterer {

	@Override
	default void receive(Parcel<?> parcel) {
		if (!filter(parcel)) { return; }
		send(parcel);
	}
	
}
