package net.realmproject.dcm.parcel.impl.routing.routingtable;

import java.util.ArrayList;
import java.util.List;

import net.realmproject.dcm.parcel.core.ParcelPath;

public class Route implements ParcelPath {

	private String target = null;
	
	// Path format: next hop, node, node, ...
	private List<String> path = new ArrayList<>();
	
	private long expiry = System.currentTimeMillis() + 10000;
	

	public Route(String hop) {
		target = hop;
	}

	
	public Route(String firstHop, Route route) {
		path.add(firstHop);
		path.addAll(route.getRoute());
		target = route.target;
	}

	public long getExpiry() {
		return expiry;
	}
	
	public boolean isExpired() {
		return expiry < System.currentTimeMillis();
	}
	
	public void extendExpiry() {
		expiry = System.currentTimeMillis() + 10000;
	}
	
	public int getHopCount() {
		return path.size();
	}
	
	public String getTarget() {
		return target;
	}
	
	public String getNextHop() {
		if (path.size() > 0) {
			return path.get(0);
		} else {
			return target;
		}
	}

	@Override
	public List<String> getRoute() {
		return path;
	}
	
		
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Route)) { return false; }
		Route otherRoute = (Route) other;
		
		if (!(otherRoute.target.equals(target))) { return false; }
		if (!(otherRoute.path.equals(path))) { return false; }
		return true;
	}
	
}