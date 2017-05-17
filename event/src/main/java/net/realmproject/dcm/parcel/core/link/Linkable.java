package net.realmproject.dcm.parcel.core.link;

import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;

public interface Linkable extends ParcelSender, ParcelReceiver {

	void setReceiver(ParcelReceiver receiver);

	ParcelReceiver getReceiver();
	
	default Linkable link(Linkable link) {
		setReceiver(link);
		return link;
	}
	
	default void link(ParcelReceiver receiver) {
		setReceiver(receiver);
	}
	
}
