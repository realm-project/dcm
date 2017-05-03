package net.realmproject.dcm.parcel.router;

import java.util.Collection;

public interface RoutingTable {

	public void addLocal(String id);
	public void addRoute(String target, String nextHop, int hops);
	
	/**
	 * Increments the hop counts by 1 and sets all next hops to this node
	 * @param hop
	 */
	public void hop(String hop);
	
	public void add(RoutingTable rotues);
	
	public Route nextHop(String destination);
	
	public Collection<String> getDestinations();
	
}
