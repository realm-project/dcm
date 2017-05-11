package net.realmproject.dcm.stock.breakout;

import net.realmproject.dcm.network.WireSink;
import net.realmproject.dcm.network.WireSource;
import net.realmproject.dcm.network.impl.DummyWireMessageSource;
import net.realmproject.dcm.network.impl.IWireSink;
import net.realmproject.dcm.parcel.core.hub.ParcelHub;
import net.realmproject.dcm.parcel.core.routing.ParcelRouter;
import net.realmproject.dcm.parcel.impl.hub.IParcelBridge;
import net.realmproject.dcm.parcel.impl.hub.IParcelHub;
import net.realmproject.dcm.parcel.impl.routing.IParcelRouter;
import net.realmproject.dcm.parcel.impl.routing.IRoutingParcelBridge;
import net.realmproject.dcm.parcel.impl.routing.IRoutingParcelConsumer;

public class Breakout {

	public static void main(String[] args) {
		
		//WireSource/Sink
		ParcelHub frontend = new IParcelHub();
		ParcelHub backend = new IParcelHub();

		WireSink backSink = new IWireSink(backend);
		WireSink frontSink = new IWireSink(frontend);
		WireSource frontSource = new DummyWireMessageSource(frontend, backSink);
		WireSource backSource = new DummyWireMessageSource(backend, frontSink);
		
		IParcelBridge bridge = new IParcelBridge(frontend, backend);
		
		
//		//Routers
//		ParcelRouter frontend = new IParcelRouter();
//		frontend.setId("frontend-router");
//		ParcelRouter backend = new IParcelRouter();
//		backend.setId("backend-router");		
//		IRoutingParcelBridge bridge = new IRoutingParcelBridge(frontend, backend);
		

		
		
		
		frontend.subscribe(new IRoutingParcelConsumer("useless", p -> {}));

		
		BreakoutEngine breakout = new BreakoutEngine("breakout-engine", backend);
		SwingUI ui = new SwingUI("breakout-screen", frontend);
		
		
	}
	
	
}
