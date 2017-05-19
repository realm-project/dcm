package net.realmproject.dcm.parcel.core.linkable;

import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;

public interface Linkable extends ParcelSender, ParcelReceiver {

	default Linkable link(Linkable link) {
		link((ParcelReceiver) link);
		return link;
	}
	default ListLinkable link(ListLinkable link) {
		link((ParcelReceiver) link);
		return link;
	}
	default NamedLinkable link(NamedLinkable link) {
		link((ParcelReceiver) link);
		return link;
	}
	
	
	
	
	void link(ParcelReceiver receiver);
	void unlink();
	ParcelReceiver getLink();
	
}
