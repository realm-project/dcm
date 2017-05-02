package net.realmproject.dcm.stock.breakout;

import net.realmproject.dcm.network.WireMessageSink;
import net.realmproject.dcm.network.WireMessageSource;
import net.realmproject.dcm.network.impl.DummyWireMessageSource;
import net.realmproject.dcm.network.impl.IWireMessageSink;
import net.realmproject.dcm.parcel.bus.IParcelBridge;
import net.realmproject.dcm.parcel.bus.IParcelHub;
import net.realmproject.dcm.parcel.bus.ParcelHub;

public class Breakout {

	public static void main(String[] args) {
		
		ParcelHub frontHub = new IParcelHub();
		ParcelHub backHub = new IParcelHub();

		
		WireMessageSink backSink = new IWireMessageSink(backHub);
		WireMessageSink frontSink = new IWireMessageSink(frontHub);
		
		WireMessageSource frontSource = new DummyWireMessageSource(frontHub, backSink);
		WireMessageSource backSource = new DummyWireMessageSource(backHub, frontSink);
		
		//IParcelBridge bridge = new IParcelBridge(frontHub, backHub);
		
		
		
		frontHub.subscribe(p -> System.out.println(p));
		
		
		Screen breakout = new Screen("breakout-backend", frontHub);
		SwingUI ui = new SwingUI(frontHub);
		
	}
	
	
}
