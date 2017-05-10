package net.realmproject.dcm.parcel.node.link;

import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.node.hub.ParcelHub;
import net.realmproject.dcm.parcel.node.receiver.ParcelReceiver;
import net.realmproject.dcm.util.DCMThreadPool;

public class IAsyncParcelLink extends IParcelLink {

	public IAsyncParcelLink(ParcelReceiver to) {
		super(to);
	}

	public IAsyncParcelLink(ParcelHub from, ParcelReceiver to) {
		super(from, to);
		
	}

	@Override
	public void receive(Parcel<?> parcel) {
		if (!parcel.visit(getId())) { return; } // cycle detection
		DCMThreadPool.getPool().submit(() -> to.receive(parcel));
	}

}
