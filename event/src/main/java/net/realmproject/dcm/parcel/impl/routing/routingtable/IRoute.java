package net.realmproject.dcm.parcel.impl.routing.routingtable;

import java.util.ArrayList;
import java.util.List;

import net.realmproject.dcm.parcel.core.routing.Route;


public class IRoute implements Route {

	private String target = null;
	
	// Path format: next hop, node, node, ...
	private List<String> path = new ArrayList<>();
	
	private long expiry = System.currentTimeMillis() + 2000;
	

	public IRoute(String hop) {
		target = hop;
	}

	
	public IRoute(String firstHop, Route route) {
		path.add(firstHop);
		path.addAll(route.getPath());
		target = route.getTarget();
	}

	@Override
	public long getExpiry() {
		return expiry;
	}
	
	@Override
	public boolean isExpired() {
		return expiry < System.currentTimeMillis();
	}
	
	@Override
	public void extendExpiry() {
		expiry = System.currentTimeMillis() + 2000;
	}
	
	@Override
	public int getHopCount() {
		return path.size();
	}
	
	@Override
	public String getTarget() {
		return target;
	}
	
	@Override
	public String getNextHop() {
		if (path.size() > 0) {
			return path.get(0);
		} else {
			return target;
		}
	}

	@Override
	public List<String> getPath() {
		return path;
	}
	
		
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof IRoute)) { return false; }
		IRoute otherRoute = (IRoute) other;
		
		if (!(otherRoute.target.equals(target))) { return false; }
		if (!(otherRoute.path.equals(path))) { return false; }
		return true;
	}
	
}