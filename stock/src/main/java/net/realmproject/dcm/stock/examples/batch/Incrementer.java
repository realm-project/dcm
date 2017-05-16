package net.realmproject.dcm.stock.examples.batch;

import net.realmproject.dcm.features.service.ServicePayload;
import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;

public class Incrementer extends IParcelNode implements ParcelReceiver {

	@Override
	public void receive(Parcel<?> parcel) {
		ServicePayload<Integer> payload = ((Parcel<ServicePayload<Integer>>) parcel).getPayload();
		Integer v = (Integer) payload.getValue();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		payload.setResult(v + 1);

	}

}
