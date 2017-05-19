package net.realmproject.dcm.parcel.core.linkable;

import java.util.List;

import net.realmproject.dcm.parcel.core.ParcelReceiver;

public interface Linkable {

	List<ParcelReceiver> getLinks();
	
}
