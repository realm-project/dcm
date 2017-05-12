package net.realmproject.dcm.network.impl.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import net.realmproject.dcm.network.WireReceiver;
import net.realmproject.dcm.parcel.core.Logging;
import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;
import net.realmproject.dcm.util.DCMThreadPool;

public class SocketWireReceiver extends IParcelNode implements WireReceiver, Logging {

	int port;
	
	ServerSocket serverSocket;
	Socket socket;
	DataInputStream dataIn;
	DataOutputStream dataOut;
	
	ParcelReceiver receiver;
	
	public SocketWireReceiver(int port, ParcelReceiver receiver) {
		this.port = port;
		this.receiver = receiver;

		DCMThreadPool.getPool().submit(this::accept);
		
	}
	
	private void accept() {
		try {
			serverSocket = new ServerSocket(port);
			socket = serverSocket.accept();
			dataIn = new DataInputStream(socket.getInputStream());
			dataOut = new DataOutputStream(socket.getOutputStream());
			
			listen();
			
		} catch (Exception e) {
			getLog().error("Could not receive connection from client", e);
		}
	}
	
	private void listen() {
		try {
			while (true) {
				SocketMessageType type = SocketMessageType.values()[dataIn.readInt()];
				int length = dataIn.readInt();
				byte[] bytes = new byte[length];
				dataIn.read(bytes);
				receive(bytes);
			}
		} catch (IOException e) {
			getLog().error("Failed to read message", e);
		}
	}

	@Override
	public void send(Parcel<?> parcel) {
		receiver.receive(parcel);
	}

	@Override
	public void receive(byte[] serializedParcel) {
		send(Parcel.deserializeParcel(serializedParcel));
	}
	
}
