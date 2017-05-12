package net.realmproject.dcm.stock.breakout;

import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.network.WireSink;
import net.realmproject.dcm.network.WireSource;
import net.realmproject.dcm.network.impl.DummyWireMessageSource;
import net.realmproject.dcm.network.impl.IWireSink;
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
		
//		//WireSource/Sink
//		ParcelHub frontend = new IParcelHub();
//		ParcelHub backend = new IParcelHub();
//
//		WireSink backSink = new IWireSink(backend);
//		WireSink frontSink = new IWireSink(frontend);
//		WireSource frontSource = new DummyWireMessageSource(frontend, backSink);
//		WireSource backSource = new DummyWireMessageSource(backend, frontSink);
//		
//		IParcelBridge bridge = new IParcelBridge(frontend, backend);
		
		
		//Routers
		ParcelRouter frontend = new IParcelRouter();
		frontend.setId("front");
		ParcelRouter backend = new IParcelRouter();
		backend.setId("back");		
		IRoutingParcelBridge bridge = new IRoutingParcelBridge("bridge", frontend, backend);

		
		
		ParcelReceiver useless = new IRoutingParcelConsumer("useless", p -> {});
		frontend.subscribe(useless);

		
		BreakoutEngine breakout = new BreakoutEngine("breakout-engine", backend);
		SwingUI ui = new SwingUI("breakout-screen", frontend);
		
		DCMThreadPool.getScheduledPool().scheduleWithFixedDelay(() -> {
			System.out.println(frontend.getRoutes());
		}, 1, 1, TimeUnit.SECONDS);
		
		DCMThreadPool.getScheduledPool().schedule(() -> useless.setId("useful"), 20, TimeUnit.SECONDS);
		
		Thread.currentThread().sleep(100000);
		
	}
	
	
}
