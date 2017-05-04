package net.realmproject.dcm.parcel.router.routingtable;

import java.util.Collection;

import net.realmproject.dcm.parcel.receiver.ParcelReceiver;

public interface AutoRoutingTable extends RoutingTable {

	void addParcelReceiver(ParcelReceiver receiver);
	void removeParcelReceiver(ParcelReceiver receiver);
	void clearParcelReceivers();
	void setParcelReceivers(Collection<ParcelReceiver> receivers);
	
	
}
