package net.realmproject.dcm.parcel.impl.link;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.util.DCMThreadPool;

public class IAsyncParcelLink extends IParcelLink {

	public IAsyncParcelLink() {}
	
	@Override
	public void send(Parcel<?> parcel) {
		DCMThreadPool.getPool().submit(() -> super.send(parcel));
	}

}
