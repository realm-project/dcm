package net.realmproject.dcm.stock.breakout;

import net.realmproject.dcm.parcel.bus.IParcelHub;
import net.realmproject.dcm.parcel.bus.ParcelHub;

public class Breakout {

	public static void main(String[] args) {
		
		ParcelHub hub = new IParcelHub();
		Screen breakout = new Screen("breakout-backend", hub);
		SwingUI ui = new SwingUI(hub);
		
	}
	
	
}
