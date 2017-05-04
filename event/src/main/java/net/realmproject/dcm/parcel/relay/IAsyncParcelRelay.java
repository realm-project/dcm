package net.realmproject.dcm.parcel.relay;

import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.bus.ParcelHub;
import net.realmproject.dcm.parcel.receiver.ParcelReceiver;
import net.realmproject.dcm.util.DCMThreadPool;

public class IAsyncParcelRelay extends IParcelRelay {

	public IAsyncParcelRelay(ParcelReceiver to) {
		super(to);
	}

	public IAsyncParcelRelay(ParcelHub from, ParcelReceiver to) {
		super(from, to);
		
	}

	@Override
	public void receive(Parcel<?> parcel) {
		if (!filter(parcel)) { return; }
		if (!parcel.visit(getId())) { return; } // cycle detection
		DCMThreadPool.getPool().submit(() -> to.receive(transform(parcel)));
	}

}
