package net.realmproject.dcm.parcel.impl.flow.link;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.parcel.core.metadata.ParcelNodeType;
import net.realmproject.dcm.util.DCMThreadPool;

@ParcelMetadata (name="ThreadPool Link", type=ParcelNodeType.FORWARDER)
public class IAsyncParcelLink extends IParcelLink {

	public IAsyncParcelLink() {}
	
	@Override
	public void send(Parcel<?> parcel) {
		DCMThreadPool.getPool().submit(() -> super.send(parcel));
	}

}
