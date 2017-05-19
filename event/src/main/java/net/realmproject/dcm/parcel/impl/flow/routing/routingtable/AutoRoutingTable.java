package net.realmproject.dcm.parcel.impl.flow.routing.routingtable;

import java.util.Collection;

import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.flow.routing.RoutingTable;

public interface AutoRoutingTable extends RoutingTable {

	void addParcelReceiver(ParcelReceiver receiver);
	void removeParcelReceiver(ParcelReceiver receiver);
	void clearParcelReceivers();
	void setParcelReceivers(Collection<ParcelReceiver> receivers);
	
	
}
