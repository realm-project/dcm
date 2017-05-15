package net.realmproject.dcm.network.impl.socket;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;

public class ISocketWireReceiver extends SocketWireServer implements ParcelSender {

	private ParcelReceiver receiver;
	
	public ISocketWireReceiver(int port, ParcelReceiver receiver) {
		super(port);
		this.receiver = receiver;
	}

	@Override
	public void wireReceive(byte[] bytes, int type) {
		Parcel<?> parcel = Parcel.deserializeParcel(bytes);
		send(parcel);
	}

	@Override
	public void send(Parcel<?> parcel) {
		receiver.receive(parcel);
	}

}
