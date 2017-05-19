package net.realmproject.dcm.stock.examples.breakout;

import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.network.WireBytesReceiver;
import net.realmproject.dcm.network.WireBytesSender;
import net.realmproject.dcm.network.impl.IWireReceiver;
import net.realmproject.dcm.network.impl.dummy.DummyWireSender;
import net.realmproject.dcm.network.impl.socket.routing.IRoutingSocketWireReceiver;
import net.realmproject.dcm.network.impl.socket.routing.IRoutingSocketWireSender;
import net.realmproject.dcm.network.impl.socket.routing.RoutingSocketWireReceiver;
import net.realmproject.dcm.network.impl.socket.routing.RoutingSocketWireSender;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.hub.ParcelHub;
import net.realmproject.dcm.parcel.core.routing.ParcelRouter;
import net.realmproject.dcm.parcel.impl.hub.IParcelBridge;
import net.realmproject.dcm.parcel.impl.hub.IParcelHub;
import net.realmproject.dcm.parcel.impl.routing.IParcelRouter;
import net.realmproject.dcm.parcel.impl.routing.IRoutingParcelBridge;
import net.realmproject.dcm.parcel.impl.routing.IRoutingParcelConsumer;
import net.realmproject.dcm.util.DCMThreadPool;

public class Breakout {

	public static void main(String[] args) throws InterruptedException {
		

		
		//Routers
		ParcelRouter frontend = new IParcelRouter();
		frontend.setId("front");
		ParcelRouter backend = new IParcelRouter();
		backend.setId("back");
		
		
		//Link the front router to the back router with sockets
		RoutingSocketWireSender wFrontSender = new IRoutingSocketWireSender("localhost", 3564);
		wFrontSender.setId("front-wire-sender");
		frontend.link(wFrontSender);
		RoutingSocketWireReceiver wBackReceiver = new IRoutingSocketWireReceiver(3564, backend);
		wBackReceiver.setId("back-wire-receiver");
		
		//Link the back router to the front router with sockets
		RoutingSocketWireSender wBackSender = new IRoutingSocketWireSender("localhost", 3565);
		wBackSender.setId("back-wire-sender");
		backend.link(wBackSender);
		RoutingSocketWireReceiver wFrontReceiver = new IRoutingSocketWireReceiver(3565, frontend);
		wFrontReceiver.setId("front-wire-receiver");
		
		
		DCMThreadPool.getScheduledPool().scheduleWithFixedDelay(() -> {
			System.out.println(frontend.getRoutes());
		}, 1, 1, TimeUnit.SECONDS);
		
		
		
		ParcelReceiver useless = new IRoutingParcelConsumer("useless", p -> {});
		frontend.link(useless);

		
		BreakoutEngine breakout = new BreakoutEngine("breakout-engine", backend);
		SwingUI ui = new SwingUI("breakout-screen", frontend);
		

		
		DCMThreadPool.getScheduledPool().schedule(() -> useless.setId("useful"), 20, TimeUnit.SECONDS);

		
	}
	
	
}
