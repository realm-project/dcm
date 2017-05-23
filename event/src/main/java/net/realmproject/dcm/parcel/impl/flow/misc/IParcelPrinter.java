package net.realmproject.dcm.parcel.impl.flow.misc;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;

public class IParcelPrinter extends IParcelNode implements ParcelReceiver {

	@Override
	public void receive(Parcel<?> parcel) {
		System.out.println(parcel.getPayload());
	}

	
	
}
