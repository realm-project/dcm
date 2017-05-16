package net.realmproject.dcm.network.impl.socket;

import java.io.IOException;

import net.realmproject.dcm.network.WireSender;
import net.realmproject.dcm.parcel.core.Parcel;

public class ISocketWireSender extends SocketWireClient implements WireSender {

	public static final int MESSAGE_TYPE_PARCEL=1;
	
	public ISocketWireSender(String hostname, int port) {
		super(hostname, port);
	}


	@Override
	public void receive(Parcel<?> parcel) {
		try {
			socketSend(parcel.serializeParcel(), MESSAGE_TYPE_PARCEL);
		} catch (IOException e) {
			getLog().error("Could not transmit wire message", e);
		}
	}


	@Override
	public void socketReceive(byte[] bytes, int type) {
		// Discard received wire messages
	}


	@Override
	public void wireSend(byte[] serializedParcel) {
		try {
			socketSend(serializedParcel);
		} catch (IOException e) {
			getLog().error("Failed to transmit message over socket", e);
		}
	}

}
