package net.realmproject.dcm.network.impl.socket;

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
	
	default void wireListen() {
		DataInputStream dataIn = getDataIn();
		try {
			while (true) {
				int type = dataIn.readInt();
				int length = dataIn.readInt();
				byte[] bytes = new byte[length];
				dataIn.read(bytes);
				wireReceive(bytes, type);
			}
		} catch (IOException e) {
			getLog().error("Failed to read message", e);
		}
	}
	void wireReceive(byte[] bytes, int type);
	
	default void wireSend(byte[] data, int type) throws IOException {
		DataOutputStream dataOut = getDataOut();
		dataOut.writeInt(type);
		dataOut.writeInt(data.length);
		dataOut.write(data);
	}
	

	
	
}
