package net.realmproject.dcm.parcel.router;


import net.realmproject.dcm.parcel.ParcelNode;
import net.realmproject.dcm.parcel.hub.ParcelHub;
import net.realmproject.dcm.parcel.receiver.ParcelReceiver;

public interface ParcelRouter extends ParcelHub, ParcelNode, ParcelReceiver, Routing {

	
}
