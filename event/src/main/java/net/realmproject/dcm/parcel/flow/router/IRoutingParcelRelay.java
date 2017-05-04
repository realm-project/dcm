package net.realmproject.dcm.parcel.flow.router;

import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.flow.relay.IParcelRelay;
import net.realmproject.dcm.parcel.flow.router.routingtable.AutoRoutingTable;
import net.realmproject.dcm.parcel.flow.router.routingtable.IAutoRoutingTable;
import net.realmproject.dcm.parcel.flow.router.routingtable.RoutingTable;
import net.realmproject.dcm.parcel.node.receiver.ParcelReceiver;

public class IRoutingParcelRelay extends IParcelRelay implements Routing {

	public AutoRoutingTable routes = new IAutoRoutingTable();

	public IRoutingParcelRelay(ParcelReceiver to) {
		super(to);
		routes.addLocal(getId());
		routes.addParcelReceiver(to);
	}

	@Override
	public void receive(Parcel<?> parcel) {

		// TODO: Fix me
		//routes.addLocal(getId());
		//routes.integrate(to);

		super.receive(parcel);

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
