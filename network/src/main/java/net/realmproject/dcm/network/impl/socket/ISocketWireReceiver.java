package net.realmproject.dcm.network.impl.socket;

import net.realmproject.dcm.network.WireReceiver;
import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;

public class ISocketWireReceiver extends SocketWireServer implements WireReceiver, ParcelSender {

	private ParcelReceiver receiver;
	
	public ISocketWireReceiver(int port, ParcelReceiver receiver) {
		super(port);
		this.receiver = receiver;
	}

	@Override
	public void socketReceive(byte[] bytes, int type) {
		wireReceive(bytes);
	}

	@Override
	public void send(Parcel<?> parcel) {
		receiver.receive(parcel);
	}

}
