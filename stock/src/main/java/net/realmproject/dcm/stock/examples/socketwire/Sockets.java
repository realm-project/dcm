package net.realmproject.dcm.stock.examples.socketwire;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;

import net.realmproject.dcm.network.impl.socket.SocketWireReceiver;
import net.realmproject.dcm.network.impl.socket.SocketWireSender;
import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.impl.parcel.IParcel;
import net.realmproject.dcm.parcel.impl.receiver.AbstractParcelReceiver;
import net.realmproject.dcm.util.DCMSettings;

public class Sockets {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		
		ParcelReceiver printer = new AbstractParcelReceiver() {
			
			@Override
			public void receive(Parcel<?> parcel) {
				System.out.println(parcel.getPayload().toString());
			}
		};
		
		SocketWireSender sender = new SocketWireSender("localhost", 3564);
		SocketWireReceiver receiver = new SocketWireReceiver(3564, printer);
		
		Thread.sleep(DCMSettings.STARTUP_DELAY * 1000);
		
		sender.receive(new IParcel<>().payload("Hello"));
		sender.receive(new IParcel<>().payload("World"));
		sender.receive(new IParcel<>().payload(new Date()));
		
		Thread.sleep(10000);
		
	}
	
}
