package net.realmproject.dcm.parcel.router;


public interface Routing {

	RoutingTable getRoutes(String previousHop);
	
	default RoutingTable getRoutes() {
		return getRoutes(null);
	}
	
}
