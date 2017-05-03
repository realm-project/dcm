package net.realmproject.dcm.parcel.receiver;

import java.util.function.Consumer;

import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.util.DCMUtil;

public class IReceiver implements ParcelReceiver {

	private String id = DCMUtil.generateId(); 
	private Consumer<Parcel<?>> consumer;

	public IReceiver(Consumer<Parcel<?>> consumer) {
		this.consumer = consumer;
	}
	
	public IReceiver(String id, Consumer<Parcel<?>> consumer) {
		this.id = id;
		this.consumer = consumer;
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void accept(Parcel<?> parcel) {
		consumer.accept(parcel);
	}

}
