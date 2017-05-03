package net.realmproject.dcm.parcel.router;

import net.realmproject.dcm.parcel.receiver.ParcelReceiver;
import net.realmproject.dcm.parcel.relay.IParcelRelay;

public class IRoutingParcelRelay extends IParcelRelay implements Routing {

	public IRoutingParcelRelay(ParcelReceiver to) {
		super(to);
	}

	@Override
	public RoutingTable getRoutes(String previousHop) {
		RoutingTable routes = new IRoutingTable();
		routes.addLocal(getId());
		if (to instanceof Routing) {
			Routing node = (Routing) to;
			RoutingTable otherRoutes = node.getRoutes(getId());
			otherRoutes.hop(to.getId());
			routes.add(otherRoutes);
		} else {
			routes.addRoute(to.getId(), getId(), 1);
		}
		routes.markTime();
		return routes;

	}

}
