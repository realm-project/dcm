package net.realmproject.dcm.stock.breakout;

import net.realmproject.dcm.network.WireSink;
import net.realmproject.dcm.network.WireSource;
import net.realmproject.dcm.network.impl.DummyWireMessageSource;
import net.realmproject.dcm.network.impl.IWireSink;
import net.realmproject.dcm.parcel.flow.hub.IParcelBridge;
import net.realmproject.dcm.parcel.flow.hub.IParcelHub;
import net.realmproject.dcm.parcel.flow.hub.ParcelHub;
import net.realmproject.dcm.parcel.flow.router.IParcelRouter;
import net.realmproject.dcm.parcel.flow.router.IRoutingParcelBridge;
import net.realmproject.dcm.parcel.flow.router.IRoutingParcelConsumer;
import net.realmproject.dcm.parcel.flow.router.IRoutingParcelRelay;
import net.realmproject.dcm.parcel.flow.router.ParcelRouter;
import net.realmproject.dcm.parcel.flow.router.routingtable.RoutingTable;
import net.realmproject.dcm.parcel.node.receiver.IParcelConsumer;

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

		
		Screen breakout = new Screen("breakout-engine", backend);
		SwingUI ui = new SwingUI("breakout-screen", frontend);
		
		
	}
	
	
}
