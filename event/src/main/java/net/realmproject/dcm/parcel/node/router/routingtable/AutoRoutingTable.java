package net.realmproject.dcm.parcel.node.router.routingtable;

import java.util.Collection;

import net.realmproject.dcm.parcel.node.receiver.ParcelReceiver;

public interface AutoRoutingTable extends RoutingTable {

	void addParcelReceiver(ParcelReceiver receiver);
	void removeParcelReceiver(ParcelReceiver receiver);
	void clearParcelReceivers();
	void setParcelReceivers(Collection<ParcelReceiver> receivers);
	
	
}
