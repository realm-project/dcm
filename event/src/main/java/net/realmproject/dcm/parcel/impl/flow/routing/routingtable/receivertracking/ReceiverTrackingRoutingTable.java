package net.realmproject.dcm.parcel.impl.flow.routing.routingtable.receivertracking;

import java.util.Collection;

import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.flow.routing.RoutingTable;

public interface ReceiverTrackingRoutingTable extends RoutingTable {

	void addParcelReceiver(ParcelReceiver receiver);
	void removeParcelReceiver(ParcelReceiver receiver);
	void clearParcelReceivers();
	void setParcelReceivers(Collection<ParcelReceiver> receivers);
	
	
}
