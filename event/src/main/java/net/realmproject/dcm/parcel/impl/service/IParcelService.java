package net.realmproject.dcm.parcel.impl.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import net.realmproject.dcm.parcel.core.Logging;
import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.parcel.core.metadata.ParcelNodeType;
import net.realmproject.dcm.parcel.core.service.ParcelService;
import net.realmproject.dcm.parcel.impl.parcel.IParcel;
import net.realmproject.dcm.parcel.impl.sender.IParcelSender;
import net.realmproject.dcm.util.DCMThreadPool;

//TODO: purge really old entries which are never fulfilled.
@ParcelMetadata (name="Service", type=ParcelNodeType.SERVICE)
public class IParcelService<F, T> extends IParcelSender implements ParcelService<F, T>, Logging {

	protected Map<String, BlockingQueue<T>> resultQueues = new HashMap<>();
	protected BlockingQueue<Parcel<F>> dispatchQueue = new LinkedBlockingQueue<>();
	
	public IParcelService() {
		DCMThreadPool.getPool().submit(() -> {
			while (true) {
				try {
					send(dispatchQueue.take());
				} catch (Exception e) {
					getLog().error("Exception thrown processing service call", e);
				}
			}
		});
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


		dispatchQueue.offer(parcel);
		
		return new IParcelServiceResult<>(queue);
	}
	

	@Override
	public synchronized void receive(Parcel<?> parcel) {
		String id = parcel.getId();
		if (resultQueues.containsKey(id)) {
			T result = (T) parcel.getPayload();
			resultQueues.get(id).offer(result);
			resultQueues.remove(id);
		}
	}




}
