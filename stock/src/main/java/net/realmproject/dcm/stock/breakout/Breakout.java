package net.realmproject.dcm.stock.breakout;

import net.realmproject.dcm.network.WireSink;
import net.realmproject.dcm.network.WireSource;
import net.realmproject.dcm.network.impl.DummyWireMessageSource;
import net.realmproject.dcm.network.impl.IWireSink;
import net.realmproject.dcm.parcel.bus.IParcelBridge;
import net.realmproject.dcm.parcel.bus.IParcelHub;
import net.realmproject.dcm.parcel.bus.ParcelHub;

public class Breakout {

	public static void main(String[] args) {
		
		ParcelHub frontHub = new IParcelHub();
		ParcelHub backHub = new IParcelHub();

		
		WireSink backSink = new IWireSink(backHub);
		WireSink frontSink = new IWireSink(frontHub);
		
		WireSource frontSource = new DummyWireMessageSource(frontHub, backSink);
		WireSource backSource = new DummyWireMessageSource(backHub, frontSink);
		

		frontHub.subscribe(p -> System.out.println(p.getPayload()));
		
		
		Screen breakout = new Screen("breakout-backend", frontHub);
		SwingUI ui = new SwingUI(frontHub);
		
	}
	
	
}
