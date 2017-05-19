package net.realmproject.dcm.parcel.core.flow.routing;


import net.realmproject.dcm.parcel.core.ParcelNode;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.flow.hub.ParcelHub;

public interface ParcelRouter extends ParcelHub, ParcelNode, ParcelReceiver, Routing {

	
}
