package net.realmproject.dcm.stock.examples.socketwire;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.network.impl.socket.ISocketWireReceiver;
import net.realmproject.dcm.network.impl.socket.ISocketWireSender;
import net.realmproject.dcm.network.impl.socket.routing.IRoutingSocketWireReceiver;
import net.realmproject.dcm.network.impl.socket.routing.IRoutingSocketWireSender;
import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelLink;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.impl.link.IParcelLink;
import net.realmproject.dcm.parcel.impl.parcel.IParcel;
import net.realmproject.dcm.parcel.impl.receiver.AbstractParcelReceiver;
import net.realmproject.dcm.parcel.impl.routing.IRoutingParcelLink;
import net.realmproject.dcm.util.DCMSettings;
import net.realmproject.dcm.util.DCMThreadPool;

public class Sockets {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		
		
		
		
		ParcelReceiver printer = new AbstractParcelReceiver() {
			
			@Override
			public void receive(Parcel<?> parcel) {
				System.out.println(parcel.getPayload().toString());
			}
		};
		printer.setId("printer");
		
		ParcelLink noop = new IRoutingParcelLink(printer);
		noop.setId("noop");
		
		
		
		IRoutingSocketWireSender sender = new IRoutingSocketWireSender("localhost", 3564);
		sender.setId("wire-sender");
		
		IRoutingSocketWireReceiver receiver = new IRoutingSocketWireReceiver(3564, noop);
		receiver.setId("wire-receiver");
		
		
		
		Thread.sleep(DCMSettings.STARTUP_DELAY * 1000);
		
		sender.receive(new IParcel<>().payload("Hello"));
		sender.receive(new IParcel<>().payload("World"));
		sender.receive(new IParcel<>().payload(new Date()));
		
		DCMThreadPool.getScheduledPool().scheduleWithFixedDelay(() -> {
			System.out.println(sender.getRoutes());
		}, 1, 1, TimeUnit.SECONDS);
		
		Thread.sleep(100000);
		
	}
	
}
