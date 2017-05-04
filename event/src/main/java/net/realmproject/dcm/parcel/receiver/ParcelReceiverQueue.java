package net.realmproject.dcm.parcel.receiver;

import java.util.concurrent.LinkedBlockingQueue;

import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.util.DCMUtil;

public class ParcelReceiverQueue extends LinkedBlockingQueue<Parcel<?>> implements ParcelReceiver {

	private String id = DCMUtil.generateId();
	
	public ParcelReceiverQueue() {
		super();
	}
	
	public ParcelReceiverQueue(int capacity) {
		super(capacity);
	}
	
	@Override
	public void receive(Parcel<?> parcel) {
		offer(parcel);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

}
