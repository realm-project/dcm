package net.realmproject.dcm.network.impl.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import net.realmproject.dcm.parcel.impl.node.IParcelNode;
import net.realmproject.dcm.util.DCMThreadPool;

public abstract class SocketWireServer extends IParcelNode implements SocketWireNode {

	protected final int port;
	
	protected ServerSocket serverSocket;
	protected Socket socket;
	protected DataInputStream dataIn;
	protected DataOutputStream dataOut;
	
	
	public SocketWireServer(int port) {
		this.port = port;
		DCMThreadPool.getPool().submit(this::socketAccept);
	}
	
	
	protected void socketAccept() {
		try {
			serverSocket = new ServerSocket(port);
			socket = serverSocket.accept();
			dataIn = new DataInputStream(socket.getInputStream());
			dataOut = new DataOutputStream(socket.getOutputStream());
			
			socketListen();
			
		} catch (Exception e) {
			getLog().error("Could not receive connection from client", e);
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
