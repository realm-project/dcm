package net.realmproject.dcm.parcel.impl.link;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.hub.ParcelHub;
import net.realmproject.dcm.util.DCMThreadPool;

public class IAsyncParcelLink extends IParcelLink {

	public IAsyncParcelLink() {}
	
	public IAsyncParcelLink(ParcelHub from) {
		super(from);
		
	}

	@Override
	public void send(Parcel<?> parcel) {
		if (!parcel.visit(getId())) { return; } // cycle detection
		DCMThreadPool.getPool().submit(() -> receiver.receive(parcel));
	}

}
