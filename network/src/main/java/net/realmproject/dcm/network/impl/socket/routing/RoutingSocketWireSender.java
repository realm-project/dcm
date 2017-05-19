package net.realmproject.dcm.network.impl.socket.routing;

import net.realmproject.dcm.network.impl.socket.SocketWireNode;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.flow.routing.Routing;

public interface RoutingSocketWireSender extends SocketWireNode, ParcelReceiver, Routing {

}
