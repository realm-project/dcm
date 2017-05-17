package net.realmproject.dcm.parcel.impl.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;
import net.realmproject.dcm.parcel.core.service.ParcelService;
import net.realmproject.dcm.parcel.impl.parcel.IParcel;
import net.realmproject.dcm.parcel.impl.sender.IParcelSender;

//TODO: purge really old entries which are never fulfilled.
public class IParcelService<F, T> extends IParcelSender implements ParcelSender, ParcelReceiver, ParcelService<F, T> {

	Map<String, BlockingQueue<T>> resultQueues = new HashMap<>();

	public IParcelService(ParcelReceiver receiver) {
		super(receiver);
	}

	@Override
	public Future<T> call(F input) {
		Parcel<F> parcel = new IParcel<>();
		parcel.setPayload(input);

		// set up the listening queue before sending the parcel so we don't miss
		// the response
		String id = parcel.getId();
		BlockingQueue<T> queue = new LinkedBlockingQueue<>(1);
		resultQueues.put(id, queue);

		send(parcel);
		return new IParcelServiceResult<>(queue);
	}

	@Override
	public void receive(Parcel<?> parcel) {
		String id = parcel.getId();
		if (resultQueues.containsKey(id)) {
			T result = (T) parcel.getPayload();
			resultQueues.get(id).offer(result);
			resultQueues.remove(id);
		}
	}

}
