package net.realmproject.dcm.parcel.impl.flow.routing;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.flow.routing.Routing;
import net.realmproject.dcm.parcel.core.flow.routing.RoutingTable;
import net.realmproject.dcm.parcel.impl.flow.link.IParcelLink;
import net.realmproject.dcm.parcel.impl.flow.routing.routingtable.AutoRoutingTable;
import net.realmproject.dcm.parcel.impl.flow.routing.routingtable.IAutoRoutingTable;

public class IRoutingParcelLink extends IParcelLink implements Routing {

	public AutoRoutingTable routes = new IAutoRoutingTable(this);

	public IRoutingParcelLink(ParcelReceiver to) {
		setReceiver(to);
		routes.addParcelReceiver(to);
	}

	@Override
	public void send(Parcel<?> parcel) {
		super.send(parcel);
	}

	@Override
	public RoutingTable getRoutes() {
		return routes;

	}

}
