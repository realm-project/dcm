package net.realmproject.dcm.parcel.impl.routing.routingtable;

import java.util.Collection;

import net.realmproject.dcm.parcel.core.ParcelReceiver;

public interface RoutingTable {
	
	public Collection<String> getDestinations();
	public Route nextHop(String destination);
	
	
	public void add(RoutingTable rotues);
	public void addLocal(String id);
	public void addRoute(Route route);
	
	
	public void integrate(ParcelReceiver adjacent);
		
	
	
	
	
	
	
	/**
	 * Gets the age of this routing table in seconds.
	 * @return a long value representing the number of second since this routing table was generated. 
	 */
	public int getAge();
	
	/**
	 * Sets this Routing Table's internal timestamp to the current time.
	 */
	public void markTime();
	
}
