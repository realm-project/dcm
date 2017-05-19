package net.realmproject.dcm.network.impl.socket.routing;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.network.impl.socket.ISocketWireReceiver;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.flow.routing.RoutingTable;
import net.realmproject.dcm.parcel.impl.flow.routing.routingtable.IAutoRoutingTable;
import net.realmproject.dcm.parcel.impl.flow.routing.routingtable.IRoutingTable;
import net.realmproject.dcm.parcel.impl.identity.StubIdentity;
import net.realmproject.dcm.util.DCMSettings;
import net.realmproject.dcm.util.DCMThreadPool;
import net.realmproject.dcm.util.DCMUtil;

public class IRoutingSocketWireReceiver extends ISocketWireReceiver implements RoutingSocketWireReceiver {

	private IAutoRoutingTable routes;
	
	public IRoutingSocketWireReceiver(int port, ParcelReceiver receiver) {
		super(port, receiver);
		
		routes = new IAutoRoutingTable(this);
		routes.addParcelReceiver(receiver);
		DCMThreadPool.getScheduledPool().scheduleAtFixedRate(this::sendRoutes, DCMSettings.STARTUP_DELAY, 1, TimeUnit.SECONDS);
	}

	private void sendRoutes() {
		try {
			//AutoRoutingTable won't serialize
			IRoutingTable copy = new IRoutingTable(new StubIdentity(this.getId()), routes);
			socketSend(DCMUtil.serialize(copy), IRoutingSocketWireSender.MESSAGE_TYPE_ROUTES);
		} catch (IOException e) {
			e.printStackTrace();
			getLog().error("Failed to transmit routing table to WireReceiver", e);
		}
	}
	
	
	@Override
	public RoutingTable getRoutes() {
		return routes;
	}

}
