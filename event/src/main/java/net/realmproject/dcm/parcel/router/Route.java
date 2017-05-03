package net.realmproject.dcm.parcel.router;

public class Route {

	protected int hops;
	protected String nextHop;
	
	public Route(String nextHop, int hops) {
		this.hops = hops;
		this.nextHop = nextHop;
	}

	public int getHops() {
		return hops;
	}

	public String getNextHop() {
		return nextHop;
	}
	
}