package net.realmproject.dcm.parcel.impl.flow.routing.routingtable;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import net.realmproject.dcm.parcel.core.Identity;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.flow.routing.Route;
import net.realmproject.dcm.parcel.core.flow.routing.Routing;
import net.realmproject.dcm.parcel.core.flow.routing.RoutingTable;

public class IRoutingTable implements RoutingTable {

	
	
	private Map<String, Route> routes = new HashMap<>();
	protected Identity owner;
	
	public IRoutingTable(Identity owner) {
		this.owner = owner;
		addLocal(owner.getId());
	}

	public IRoutingTable(Identity owner, IRoutingTable other) {
		this.owner = owner;
		this.routes = new HashMap<>(other.routes);
	}
	
	/**
	 * Take an existing RoutingTable and copy it, prepending a new hop in front of all of it's routes
	 * @param hop the new first hop for all routes
	 * @param toCopy the RoutingTable to copy
	 */
	public IRoutingTable(Identity owner, String hop, RoutingTable toCopy) {
		this.owner = owner;
		for (String destination : toCopy.getDestinations()) {
			Route r = toCopy.routeTo(destination);
			//don't include routes which already contain this hop, or this node
			if (r.hasVisited(hop)) { continue; }
			if (r.hasVisited(owner.getId())) { continue; }
			routes.put(destination, new IRoute(hop, r));
		}
	}
	
	@Override
	public Route routeTo(String destination) {
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
	
	
	private String repeat(String with, int count) {
		 return new String(new char[count]).replace("\0", with);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		DecimalFormat df = new DecimalFormat("#.##");
		

		int wd=6, wn=8, wp=4, we=6;
		for (String dest : routes.keySet()) {
			Route r = routes.get(dest);
			wd = Math.max(wd, dest.length());
			wn = Math.max(wn, r.getNextHop().length());
			wp = Math.max(wp, r.getPath().toString().length());
		}
		String format = "| %-" + wd + "s | %-" + wn + "s | %-" + wp + "s | %-" + we + "s |%n";
		String bar = "+-" + repeat("-", wd) + "-+-" + repeat("-", wn) + "-+-" + repeat("-", wp) + "-+-" + repeat("-", we) + "-+\n";
		
		sb.append(bar);
		formatter.format(format, "Target", "Next Hop", "Path", "TTL");
		sb.append(bar);

		for (String dest : routes.keySet()) {
			Route r = routes.get(dest);
			float ttl = ((r.getExpiry() - System.currentTimeMillis()) / 1000f);
			formatter.format(format, dest, r.getNextHop(), r.getPath(), df.format(ttl));
		}
		
				
		sb.append(bar);
		
		
		formatter.close();
		return sb.toString();
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
			addRoute(routes.routeTo(target));
		}
	}

	
	@Override
	public void addLocal(String id) {
		addRoute(new IRoute(id));
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

	@Override
	public Identity getOwner() {
		return owner;
	}


	
}

