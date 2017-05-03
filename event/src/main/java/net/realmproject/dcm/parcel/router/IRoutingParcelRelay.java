package net.realmproject.dcm.parcel.router;

import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.receiver.ParcelReceiver;
import net.realmproject.dcm.parcel.relay.IParcelRelay;

public class IRoutingParcelRelay extends IParcelRelay implements Routing {

	public RoutingTable routes = new IRoutingTable();

	public IRoutingParcelRelay(ParcelReceiver to) {
		super(to);
	}

	@Override
	public void accept(Parcel<?> parcel) {

		// TODO: Fix me
		routes.addLocal(getId());
		routes.integrate(to);

		super.accept(parcel);

	}

	@Override
	public RoutingTable getRoutes() {
		// RoutingTable routes = new IRoutingTable();
		// routes.addLocal(getId());
		// if (to instanceof Routing) {
		// Routing node = (Routing) to;
		// RoutingTable otherRoutes = node.getRoutes(getId());
		// otherRoutes.hop(to.getId());
		// routes.add(otherRoutes);
		// } else {
		// routes.addRoute(to.getId(), getId(), 1);
		// }
		// routes.markTime();
		// return routes;
		return routes;

	}

}
