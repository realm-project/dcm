package net.realmproject.dcm.parcel.node.receiver;

import java.util.function.Consumer;

import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.util.DCMUtil;


/**
 * Wraps a Java functional {@link Consumer} in a ParcelReceiver interface 
 * @author NAS
 *
 */
public class IParcelConsumer implements ParcelReceiver {

	private String id = DCMUtil.generateId(); 
	private Consumer<Parcel<?>> consumer;


	public IParcelConsumer(String id, Consumer<Parcel<?>> consumer) {
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
	public void receive(Parcel<?> parcel) {
		consumer.accept(parcel);
	}

}
