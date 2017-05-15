package net.realmproject.dcm.network.impl.socket.routing;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;

import net.realmproject.dcm.network.impl.socket.ISocketWireSender;
import net.realmproject.dcm.parcel.core.routing.Route;
import net.realmproject.dcm.parcel.core.routing.Routing;
import net.realmproject.dcm.parcel.core.routing.RoutingTable;
import net.realmproject.dcm.parcel.impl.routing.routingtable.IAutoRoutingTable;
import net.realmproject.dcm.parcel.impl.routing.routingtable.IRoutingTable;
import net.realmproject.dcm.util.DCMUtil;

public class IRoutingSocketWireSender extends ISocketWireSender implements Routing {

	public static int MESSAGE_TYPE_ROUTES = 2;
	
	protected RoutingTable routes;
	
	public IRoutingSocketWireSender(String hostname, int port) throws UnknownHostException, IOException {
		super(hostname, port);
		routes = new IAutoRoutingTable(this);
	}
	
	@Override
	public void wireReceive(byte[] bytes, int type) {
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
