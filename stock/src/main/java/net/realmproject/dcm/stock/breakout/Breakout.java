package net.realmproject.dcm.stock.breakout;

import net.realmproject.dcm.network.WireSink;
import net.realmproject.dcm.network.WireSource;
import net.realmproject.dcm.network.impl.DummyWireMessageSource;
import net.realmproject.dcm.network.impl.IWireSink;
import net.realmproject.dcm.parcel.bus.IParcelBridge;
import net.realmproject.dcm.parcel.bus.IParcelHub;
import net.realmproject.dcm.parcel.bus.ParcelHub;
import net.realmproject.dcm.parcel.receiver.IParcelConsumer;
import net.realmproject.dcm.parcel.router.IParcelRouter;
import net.realmproject.dcm.parcel.router.IRoutingParcelBridge;
import net.realmproject.dcm.parcel.router.IRoutingParcelConsumer;
import net.realmproject.dcm.parcel.router.IRoutingParcelRelay;
import net.realmproject.dcm.parcel.router.ParcelRouter;
import net.realmproject.dcm.parcel.router.RoutingTable;

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
		

		frontend.subscribe(new IRoutingParcelConsumer(p -> {}));

		
		Screen breakout = new Screen("breakout-engine", backend);
		SwingUI ui = new SwingUI("breakout-screen", frontend);
		
		
	}
	
	
}
