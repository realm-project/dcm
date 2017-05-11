package net.realmproject.dcm.parcel.impl.routing;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.routing.Routing;
import net.realmproject.dcm.parcel.impl.link.IParcelLink;
import net.realmproject.dcm.parcel.impl.routing.routingtable.AutoRoutingTable;
import net.realmproject.dcm.parcel.impl.routing.routingtable.IAutoRoutingTable;
import net.realmproject.dcm.parcel.impl.routing.routingtable.RoutingTable;

public class IRoutingParcelRelay extends IParcelLink implements Routing {

	public AutoRoutingTable routes = new IAutoRoutingTable();

	public IRoutingParcelRelay(ParcelReceiver to) {
		setReceiver(to);
		routes.addLocal(getId());
		routes.addParcelReceiver(to);
	}

	@Override
	public void send(Parcel<?> parcel) {

		// TODO: Fix me
		//routes.addLocal(getId());
		//routes.integrate(to);

		super.send(parcel);

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
