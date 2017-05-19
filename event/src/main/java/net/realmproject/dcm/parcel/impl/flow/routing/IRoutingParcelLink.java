package net.realmproject.dcm.parcel.impl.flow.routing;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.flow.routing.Routing;
import net.realmproject.dcm.parcel.core.flow.routing.RoutingTable;
import net.realmproject.dcm.parcel.impl.flow.link.IParcelLink;
import net.realmproject.dcm.parcel.impl.flow.routing.routingtable.linkable.LinkableRoutingTable;

public class IRoutingParcelLink extends IParcelLink implements Routing {

	public RoutingTable routes = new LinkableRoutingTable(this);

	public IRoutingParcelLink(ParcelReceiver to) {
		setReceiver(to);
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
