package net.realmproject.dcm.parcel.impl.hub;

import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.hub.ParcelHub;
import net.realmproject.dcm.parcel.impl.link.IParcelLink;


//TODO: Remove this now that the Link API is in place?
public class IParcelHubSubscriber extends IParcelLink {
	
	public IParcelHubSubscriber(ParcelHub source, ParcelReceiver receiver) {
		super();
		setReceiver(receiver);
		source.link(this);
	}	
}
