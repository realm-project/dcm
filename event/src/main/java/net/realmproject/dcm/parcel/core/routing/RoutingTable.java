package net.realmproject.dcm.parcel.core.routing;

import java.util.Collection;

import net.realmproject.dcm.parcel.core.ParcelReceiver;

public interface RoutingTable {
	
	public Collection<String> getDestinations();
	public Route nextHop(String destination);
	
	
	public void add(RoutingTable rotues);
	public void addLocal(String id);
	public void addRoute(Route route);
	
	
	public void integrate(ParcelReceiver adjacent);
	public void trim();
	

}
