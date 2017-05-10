package net.realmproject.dcm.stock.breakout;

import net.realmproject.dcm.parcel.core.routing.ParcelRouter;
import net.realmproject.dcm.parcel.impl.routing.IParcelRouter;
import net.realmproject.dcm.parcel.impl.routing.IRoutingParcelBridge;
import net.realmproject.dcm.parcel.impl.routing.IRoutingParcelConsumer;

public class Breakout {

	public static void main(String[] args) {
		
//		ParcelHub frontHub = new IParcelHub();
//		ParcelHub backHub = new IParcelHub();

		
//		WireSink backSink = new IWireSink(backHub);
//		WireSink frontSink = new IWireSink(frontHub);
//		
//		WireSource frontSource = new DummyWireMessageSource(frontHub, backSink);
//		WireSource backSource = new DummyWireMessageSource(backHub, frontSink);

		
//		IParcelBridge bridge = new IParcelBridge(frontHub, backHub);
		
		
		ParcelRouter frontend = new IParcelRouter();
		frontend.setId("frontend-router");
		ParcelRouter backend = new IParcelRouter();
		backend.setId("backend-router");
		
		
		IRoutingParcelBridge bridge = new IRoutingParcelBridge(frontend, backend);
		

		frontend.subscribe(new IRoutingParcelConsumer("useless", p -> {}));

		
		BreakoutEngine breakout = new BreakoutEngine("breakout-engine", backend);
		SwingUI ui = new SwingUI("breakout-screen", frontend);
		
		
	}
	
	
}
