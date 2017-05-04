package net.realmproject.dcm.parcel.router;

import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.receiver.ParcelReceiver;
import net.realmproject.dcm.parcel.relay.IParcelRelay;
import net.realmproject.dcm.parcel.router.routingtable.AutoRoutingTable;
import net.realmproject.dcm.parcel.router.routingtable.IAutoRoutingTable;
import net.realmproject.dcm.parcel.router.routingtable.RoutingTable;

public class IRoutingParcelRelay extends IParcelRelay implements Routing {

	public AutoRoutingTable routes = new IAutoRoutingTable();

	public IRoutingParcelRelay(ParcelReceiver to) {
		super(to);
		routes.addLocal(getId());
		routes.addParcelReceiver(to);
	}

	@Override
	public void accept(Parcel<?> parcel) {

		// TODO: Fix me
		//routes.addLocal(getId());
		//routes.integrate(to);

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
