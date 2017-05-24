package net.realmproject.dcm.parcel.impl.flow.hub;

import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.flow.hub.ParcelHub;
import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.parcel.core.metadata.ParcelNodeType;
import net.realmproject.dcm.parcel.impl.flow.link.IParcelLink;


//TODO: Remove this now that the Link API is in place?
@ParcelMetadata (name="Hub Subscriber Link", type=ParcelNodeType.OTHER)
public class IParcelHubSubscriber extends IParcelLink {
	
	public IParcelHubSubscriber(ParcelHub source, ParcelReceiver receiver) {
		super();
		setReceiver(receiver);
		source.link(this);
	}	
}
