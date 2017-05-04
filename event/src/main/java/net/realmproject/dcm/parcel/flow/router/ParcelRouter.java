package net.realmproject.dcm.parcel.flow.router;


import net.realmproject.dcm.parcel.flow.hub.ParcelHub;
import net.realmproject.dcm.parcel.node.ParcelNode;
import net.realmproject.dcm.parcel.node.receiver.ParcelReceiver;

public interface ParcelRouter extends ParcelHub, ParcelNode, ParcelReceiver, Routing {

	
}
