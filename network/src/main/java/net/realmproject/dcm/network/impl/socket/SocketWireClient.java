package net.realmproject.dcm.network.impl.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.parcel.impl.node.IParcelNode;
import net.realmproject.dcm.util.DCMSettings;
import net.realmproject.dcm.util.DCMThreadPool;

public abstract class SocketWireClient extends IParcelNode implements SocketWireNode {

	protected final String hostname;
	protected final int port;

	protected Socket socket;
	protected DataInputStream dataIn;
	protected DataOutputStream dataOut;
	
	public SocketWireClient(String hostname, int port) {
		this.port = port;
		this.hostname = hostname;
		
		DCMThreadPool.getScheduledPool().schedule(this::socketConnect, DCMSettings.CREATION_DELAY, TimeUnit.SECONDS);
		
	}

	private void socketConnect() {
		try {
			socket = new Socket(hostname, port);
			dataIn = new DataInputStream(socket.getInputStream());
			dataOut = new DataOutputStream(socket.getOutputStream());
			
			socketListen();
			
		} catch (Exception e) {
			getLog().error("Failed to connect to server", e);
		}
	}
	
	@Override
	public Socket getSocket() {
		return socket;
	}

	@Override
	public DataInputStream getDataIn() {
		return dataIn;
	}

	@Override
	public DataOutputStream getDataOut() {
		return dataOut;
	}

	
}
