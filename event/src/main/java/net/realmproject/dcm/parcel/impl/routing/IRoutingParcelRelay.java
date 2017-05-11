package net.realmproject.dcm.parcel.impl.routing;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.routing.Routing;
import net.realmproject.dcm.parcel.impl.link.IParcelLink;
import net.realmproject.dcm.parcel.impl.routing.routingtable.AutoRoutingTable;
import net.realmproject.dcm.parcel.impl.routing.routingtable.IAutoRoutingTable;
import net.realmproject.dcm.parcel.impl.routing.routingtable.RoutingTable;

public class IRoutingParcelRelay extends IParcelLink implements Routing {

	public AutoRoutingTable routes = new IAutoRoutingTable(this);

	public IRoutingParcelRelay(ParcelReceiver to) {
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
