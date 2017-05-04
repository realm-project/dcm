package net.realmproject.dcm.parcel.router.routingtable;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import net.realmproject.dcm.parcel.receiver.ParcelReceiver;
import net.realmproject.dcm.parcel.router.Routing;

public class IRoutingTable implements RoutingTable {

	
	long timestamp = 0l;
	
	Map<String, Route> routes = new HashMap<>();
	
	public IRoutingTable() { }
	
	public IRoutingTable(RoutingTable toCopy) { 
		add(toCopy);
	}
	
	@Override
	public void addLocal(String id) {
		addRoute(id, id, 0);
	}

	@Override
	public void addRoute(String target, String nextHop, int hops) {
		if (target == null) { return; }
		if (routes.containsKey(target)) {
			
			Route current = routes.get(target);
			if (current.hops > hops) {
				routes.put(target, new Route(nextHop, hops));
			}
			
		} else {
			routes.put(target, new Route(nextHop, hops));
		}
	}

	@Override
	public void hop(String id) {
		for (Route r : routes.values()) {
			r.hops++;
			r.nextHop = id;
		}
	}
	
	public void integrate(ParcelReceiver adjacent) {
		RoutingTable other;
		if (adjacent instanceof Routing) {
			Routing routing = (Routing) adjacent;
			other = new IRoutingTable(routing.getRoutes());
			
		} else {
			other = new IRoutingTable();
			other.addLocal(adjacent.getId());
		}
		other.hop(adjacent.getId());
		add(other);

	}
	

	@Override
	public void add(RoutingTable routes) {
		for (String target : routes.getDestinations()) {
			Route r = routes.nextHop(target);
			addRoute(target, r.nextHop, r.hops);
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
			out += dest + "\t\t" + routes.get(dest).nextHop + "\t\t" + routes.get(dest).hops + "\n";
		}
		return out;
	}

	@Override
	public int getAge() {
		return (int) ((System.currentTimeMillis() - timestamp) / 1000.0);
	}

	@Override
	public void markTime() {
		timestamp = System.currentTimeMillis();
	}
	
	
}

