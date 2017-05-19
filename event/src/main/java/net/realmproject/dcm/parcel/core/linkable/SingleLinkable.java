package net.realmproject.dcm.parcel.core.linkable;

import java.util.Collections;
import java.util.List;

import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;

public interface SingleLinkable extends ParcelSender, ParcelReceiver {

	default SingleLinkable link(SingleLinkable link) {
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
	
	
	@Override
	default List<ParcelReceiver> getLinks() {
		return Collections.singletonList(getLink());
	}
	
	
	void link(ParcelReceiver receiver);
	void unlink();
	ParcelReceiver getLink();
	
}
