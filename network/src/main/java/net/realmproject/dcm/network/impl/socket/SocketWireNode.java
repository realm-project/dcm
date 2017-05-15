package net.realmproject.dcm.network.impl.socket;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import net.realmproject.dcm.parcel.core.Logging;
import net.realmproject.dcm.parcel.core.ParcelNode;

public interface SocketWireNode extends ParcelNode, Logging {

	Socket getSocket();
	DataInputStream getDataIn();
	DataOutputStream getDataOut();
	
	default void socketListen() {
		DataInputStream dataIn = getDataIn();
		try {
			while (true) {
				int type = dataIn.readInt();
				int length = dataIn.readInt();
				byte[] bytes = new byte[length];
				dataIn.read(bytes);
				socketReceive(bytes, type);
			}
		} catch (IOException e) {
			getLog().error("Failed to read message", e);
		}
	}
	void socketReceive(byte[] bytes, int type);
	
	default void socketSend(byte[] data) throws IOException {
		DataOutputStream dataOut = getDataOut();
		dataOut.write(data);
	}
	
	default void socketSend(byte[] data, int type) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dataOut = new DataOutputStream(baos);
		dataOut.writeInt(type);
		dataOut.writeInt(data.length);
		dataOut.write(data);
		socketSend(baos.toByteArray());
	}
	

	
	
}
