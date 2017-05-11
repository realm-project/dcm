package net.realmproject.dcm.parcel.impl.routing.routingtable;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import net.realmproject.dcm.parcel.core.Identity;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.routing.Routing;

public class IRoutingTable implements RoutingTable {

	
	
	private Map<String, Route> routes = new HashMap<>();
	protected Identity owner;
	
	public IRoutingTable(Identity owner) {
		this.owner = owner;
		addLocal(owner.getId());
	}
	
	/**
	 * Take an existing RoutingTable and copy it, prepending a new hop in front of all of it's routes
	 * @param hop the new first hop for all routes
	 * @param toCopy the RoutingTable to copy
	 */
	public IRoutingTable(Identity owner, String hop, RoutingTable toCopy) {
		this.owner = owner;
		for (String destination : toCopy.getDestinations()) {
			Route r = toCopy.nextHop(destination);
			//don't include routes which already contain this hop, or this node
			if (r.hasVisited(hop)) { continue; }
			if (r.hasVisited(owner.getId())) { continue; }
			routes.put(destination, new Route(hop, r));
		}
	}
	
	@Override
	public Route nextHop(String destination) {
		if (destination == null) { return null; }
		if (routes.containsKey(destination)) {
			return routes.get(destination);
		}
		return null;
	}

	@Override
	public Collection<String> getDestinations() {
		return new HashSet<>(routes.keySet());
	}
	
	@Override
	public String toString() {
		String out = "";
		for (String dest : routes.keySet()) {
			out += dest + "\t\t" + routes.get(dest).getNextHop() + "\t\t" + routes.get(dest).getRoute() + "\n";
		}
		return out;
	}
	
	@Override
	public void trim() {
		for (String dest : getDestinations()) {
			Route r = routes.get(dest);
			if (r.isExpired()) {
				routes.remove(dest);
			}
		}
	}
	
	/**
	 * Integrate an adjacent receiver's routing information into this routing table
	 */
	public void integrate(ParcelReceiver adjacent) {
		RoutingTable other;
		if (adjacent instanceof Routing) {
			Routing routing = (Routing) adjacent;
			//Make a copy of the routing table from our perspective
			other = new IRoutingTable(owner, adjacent.getId(), routing.getRoutes());
		} else {
			//build a bare-bones routing table from the perspective of the adjacent node
			other = new IRoutingTable(adjacent);
			//Then make a copy of the routing table from our perspective
			other = new IRoutingTable(owner, adjacent.getId(), other);
		}
		add(other);
	}
	
	@Override
	public void add(RoutingTable routes) {
		for (String target : routes.getDestinations()) {
			addRoute(routes.nextHop(target));
		}
	}

	
	@Override
	public void addLocal(String id) {
		addRoute(new Route(id));
	}

	
	/**
	 * Tries to add a route, but won't if the path is longer, or if it's own id is already in the path.
	 * The route should already be formatted to be from the perspective of this node
	 * @param route
	 */
	@Override
	public void addRoute(Route route) {
		String target = route.getTarget();
		
		if (target == null) { return; }
		if (routes.containsKey(target)) {
			
			Route current = routes.get(target);
			if (current.getHopCount() > route.getHopCount()) {
				//the new route was shorter, so use it instead
				routes.put(target, route);
			} else if (current.equals(route)) {
				//the new route was the same one, so extend the ttl
				current.extendExpiry();
			} 
//			else if (current.getHopCount() == route.getHopCount() && current.getExpiry() < route.getExpiry()) {
//				//the new route was the same length, but with a longer ttl
//				routes.put(target, route);
//			}
			
		} else {
			routes.put(target, route);
		}
	}


	
}

