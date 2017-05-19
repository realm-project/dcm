package net.realmproject.dcm.parcel.impl.hub;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;
import net.realmproject.dcm.parcel.core.hub.ParcelHub;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;

public class IParcelHubSubscriber extends IParcelNode implements ParcelReceiver, ParcelSender {

	private ParcelReceiver receiver;
	
	public IParcelHubSubscriber(ParcelHub source, ParcelReceiver receiver) {
		this.receiver = receiver;
		source.link(this);
	}
	
	@Override
	public void receive(Parcel<?> parcel) {
		send(parcel);
	}

	@Override
	public void send(Parcel<?> parcel) {
		receiver.receive(parcel);
	}

	
	
}
