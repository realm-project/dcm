package net.realmproject.dcm.parcel.core.linkable;

import java.util.List;

import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;

public interface ListLinkable extends ParcelSender, ParcelReceiver {

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
	
	
	void link(ParcelReceiver receiver);
	void unlink(ParcelReceiver receiver);
	
	default void linkAll(List<ParcelReceiver> links) {
		for (ParcelReceiver l : links) {
			link(l);
		}
	}
	
	
	default void unlinkAll() {
		for (ParcelReceiver l : getLinks()) {
			unlink(l);
		}
	}
	
}
