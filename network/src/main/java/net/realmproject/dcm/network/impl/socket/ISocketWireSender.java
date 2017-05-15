package net.realmproject.dcm.network.impl.socket;

import java.io.IOException;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;

public class ISocketWireSender extends SocketWireClient implements ParcelReceiver {

	public static final int MESSAGE_TYPE_PARCEL=1;
	
	public ISocketWireSender(String hostname, int port) {
		super(hostname, port);
	}


	@Override
	public void receive(Parcel<?> parcel) {
		try {
			wireSend(parcel.serializeParcel(), MESSAGE_TYPE_PARCEL);
		} catch (IOException e) {
			getLog().error("Could not transmit wire message", e);
		}
	}


	@Override
	public void wireReceive(byte[] bytes, int type) {
		// Discard received wire messages
	}

}
