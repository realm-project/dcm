package net.realmproject.dcm.features.service;

import java.util.concurrent.Future;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;
import net.realmproject.dcm.parcel.impl.parcel.IParcel;
import net.realmproject.dcm.util.DCMThreadPool;

public class IParcelService<F, T> extends IParcelNode implements ParcelService<F, T> {

	private ParcelReceiver start;
	
	public IParcelService(ParcelReceiver start) {
		this.start = start;
	}
	
	/* (non-Javadoc)
	 * @see net.realmproject.dcm.features.service.ParcelService#call(F)
	 */
	@Override
	public Future<T> call(F input) {
		Parcel<ServicePayload<T>> parcel = new IParcel<>(getId());
		ServicePayload<T> payload = new IServicePayload<>(input);
		parcel.setPayload(payload);
		DCMThreadPool.getPool().submit(() -> {
			start.receive(parcel);
		});
		return payload;
	}
	
}
