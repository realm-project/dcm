package net.realmproject.dcm.parcel.impl.routing.routingtable;

import java.util.ArrayList;
import java.util.List;

import net.realmproject.dcm.parcel.core.ParcelPath;

public class Route implements ParcelPath {

	private String target = null;
	
	// Path format: next hop, node, node, ...
	private List<String> path = new ArrayList<>();
	

	public Route(String hop) {
		target = hop;
	}

	
	public Route(String firstHop, Route route) {
		path.add(firstHop);
		path.addAll(route.getRoute());
		target = route.target;
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
	
}