package net.realmproject.dcm.parcel.core.linkable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;

public interface NamedLinkStart extends ParcelSender {

	default SingleLinkable link(String name, SingleLinkable link) {
		link(name, (ParcelReceiver) link);
		return link;
	}
	default ListLinkable link(String name, ListLinkable link) {
		link(name, (ParcelReceiver)link);
		return link;
	}
	default NamedLinkable link(String name, NamedLinkable link) {
		link(name, (ParcelReceiver)link);
		return link;
	}
	
	
	
	
	void link(String name, ParcelReceiver receiver);
	void unlink(String name);
	Map<String, ParcelReceiver> getNamedLinks();
	
	@Override
	default List<ParcelReceiver> getLinks() {
		return new ArrayList<>(getNamedLinks().values());
	}
	
	
	default void linkAll(Map<String, ParcelReceiver> links) {
		for (String key : links.keySet()) {
			link(key, links.get(key));
		}
	}
	default void unlinkAll() {
		Map<String, ParcelReceiver> links = getNamedLinks();
		for (String key : links.keySet()) {
			unlink(key);
		}
	}
	
	
}
