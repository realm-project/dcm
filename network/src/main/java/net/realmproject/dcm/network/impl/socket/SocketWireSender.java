package net.realmproject.dcm.network.impl.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.network.WireSender;
import net.realmproject.dcm.parcel.core.Logging;
import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;
import net.realmproject.dcm.util.DCMSettings;
import net.realmproject.dcm.util.DCMThreadPool;

public class SocketWireSender extends IParcelNode implements WireSender, Logging {

	String hostname;
	int port;
	
	Socket socket;
	DataInputStream dataIn;
	DataOutputStream dataOut;
	
	public SocketWireSender(String hostname, int port) throws UnknownHostException, IOException {
		this.hostname = hostname;
		this.port = port;
		
		DCMThreadPool.getScheduledPool().schedule(this::connect, DCMSettings.CREATION_DELAY, TimeUnit.SECONDS);
		
	}
	
	private void connect() {
		try {
			socket = new Socket(hostname, port);
			dataIn = new DataInputStream(socket.getInputStream());
			dataOut = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			getLog().error("Failed to connect to server", e);
		}
	}


	@Override
	public void send(byte[] serializedParcel) {
		try {
			dataOut.writeInt(SocketMessageType.PARCEL.ordinal());
			dataOut.writeInt(serializedParcel.length);
			dataOut.write(serializedParcel);
		} catch (IOException e) {
			getLog().error("Failed to transmit serialized parcel", e);
		}
	}


	@Override
	public void receive(Parcel<?> parcel) {
		send(parcel.serializeParcel());
	}
	
	
	
}
