package net.realmproject.dcm.stock.examples.batch;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;
import net.realmproject.dcm.parcel.impl.sender.IParcelSender;

public class Incrementer extends IParcelSender implements ParcelReceiver, ParcelSender {



	@Override
	public void receive(Parcel<?> p) {
		
		Parcel<Integer> parcel = (Parcel<Integer>) p;
		
		Integer v = parcel.getPayload();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		parcel.setPayload(v+1);
		send(parcel);

	}

}
