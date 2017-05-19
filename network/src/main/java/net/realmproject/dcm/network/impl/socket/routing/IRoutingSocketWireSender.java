package net.realmproject.dcm.network.impl.socket.routing;

import java.io.IOException;

import net.realmproject.dcm.network.impl.socket.ISocketWireSender;
import net.realmproject.dcm.parcel.core.flow.routing.RoutingTable;
import net.realmproject.dcm.parcel.impl.flow.routing.routingtable.IAutoRoutingTable;
import net.realmproject.dcm.parcel.impl.flow.routing.routingtable.IRoutingTable;
import net.realmproject.dcm.util.DCMUtil;

public class IRoutingSocketWireSender extends ISocketWireSender implements RoutingSocketWireSender {

	public static int MESSAGE_TYPE_ROUTES = 2;
	
	protected RoutingTable routes;
	
	public IRoutingSocketWireSender(String hostname, int port) {
		super(hostname, port);
		routes = new IAutoRoutingTable(this);
	}
	
	@Override
	public void socketReceive(byte[] bytes, int type) {
		if (type == MESSAGE_TYPE_ROUTES) {
			try {
				IRoutingTable rt = (IRoutingTable) DCMUtil.deserialize(bytes);
				rt = new IRoutingTable(this, rt.getOwner().getId(), rt);
				routes.add(rt);
			} catch (ClassNotFoundException | IOException e) {
				getLog().error("Could not deserialize RoutingTable", e);
			}
		}
	}


	@Override
	public RoutingTable getRoutes() {
		return routes;
	}

}
