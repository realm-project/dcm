package net.realmproject.dcm.parcel.impl.link;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.hub.ParcelHub;
import net.realmproject.dcm.util.DCMThreadPool;

public class IAsyncParcelLink extends IParcelLink {

	public IAsyncParcelLink(ParcelReceiver to) {
		super(to);
	}

	public IAsyncParcelLink(ParcelHub from, ParcelReceiver to) {
		super(from, to);
		
	}

	@Override
	public void send(Parcel<?> parcel) {
		if (!parcel.visit(getId())) { return; } // cycle detection
		DCMThreadPool.getPool().submit(() -> to.receive(parcel));
	}

}
