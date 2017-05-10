package net.realmproject.dcm.parcel.node.router.routingtable;

import java.util.Collection;

import net.realmproject.dcm.parcel.node.receiver.ParcelReceiver;

public interface RoutingTable {
	
	public void addLocal(String id);
	public void addRoute(String target, String nextHop, int hops);
	
	
	
	public void integrate(ParcelReceiver adjacent);
	
	/**
	 * Increments the hop counts by 1 and sets all next hops to this node
	 * @param hop
	 */
	public void hop(String hop);
	
	public void add(RoutingTable rotues);
	
	public Route nextHop(String destination);
	
	public Collection<String> getDestinations();
	
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
