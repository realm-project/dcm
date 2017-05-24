package net.realmproject.dcm.parcel.impl.flow.misc;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.parcel.core.metadata.ParcelNodeType;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;

@ParcelMetadata (name="Console Printer", type=ParcelNodeType.OUTPUT)
public class IParcelPrinter extends IParcelNode implements ParcelReceiver {

	@Override
	public void receive(Parcel<?> parcel) {
		System.out.println(parcel.getPayload());
	}

	
	
}
