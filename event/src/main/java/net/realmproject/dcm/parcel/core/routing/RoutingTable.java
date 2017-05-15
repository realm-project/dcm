package net.realmproject.dcm.parcel.core.routing;

import java.io.Serializable;
import java.util.Collection;

import net.realmproject.dcm.parcel.core.Identity;
import net.realmproject.dcm.parcel.core.ParcelReceiver;

public interface RoutingTable extends Serializable {
	
	Collection<String> getDestinations();
	Route routeTo(String destination);
	
	
	void add(RoutingTable rotues);
	void addLocal(String id);
	void addRoute(Route route);
	
	
	void integrate(ParcelReceiver adjacent);
	void trim();
	
	Identity getOwner();
	

}
