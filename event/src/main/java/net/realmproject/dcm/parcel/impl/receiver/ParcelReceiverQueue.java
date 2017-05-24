package net.realmproject.dcm.parcel.impl.receiver;

import java.util.concurrent.LinkedBlockingQueue;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.parcel.core.metadata.ParcelNodeType;
import net.realmproject.dcm.util.DCMUtil;

@ParcelMetadata (name="Consumer Queue", type=ParcelNodeType.QUEUE)
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
