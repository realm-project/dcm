package net.realmproject.dcm.network.impl.socket;

import java.util.Collections;
import java.util.List;

import net.realmproject.dcm.network.WireReceiver;
import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;

public class ISocketWireReceiver extends SocketWireServer implements WireReceiver {

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
    public void wireReceive(byte[] serializedParcel) {
    	Parcel<?> parcel = Parcel.deserializeParcel(serializedParcel);
        send(parcel);
    }

	@Override
	public void send(Parcel<?> parcel) {
		receiver.receive(parcel);
	}

	@Override
	public List<ParcelReceiver> getLinks() {
		return Collections.singletonList(receiver);
	}

}
