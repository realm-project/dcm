package net.realmproject.dcm.network.impl.socket.routing;

import net.realmproject.dcm.network.impl.socket.SocketWireNode;
import net.realmproject.dcm.parcel.core.ParcelSender;
import net.realmproject.dcm.parcel.core.routing.Routing;

public interface RoutingSocketWireReceiver extends SocketWireNode, ParcelSender, Routing {

}
