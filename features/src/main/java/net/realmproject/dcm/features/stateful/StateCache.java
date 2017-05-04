package net.realmproject.dcm.features.stateful;

import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.parcel.IParcel;
import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.node.IParcelNode;
import net.realmproject.dcm.parcel.node.publisher.ParcelPublisher;
import net.realmproject.dcm.parcel.node.receiver.ParcelReceiver;
import net.realmproject.dcm.util.DCMSettings;
import net.realmproject.dcm.util.DCMThreadPool;

public class StateCache extends IParcelNode implements ParcelReceiver, ParcelPublisher {

	private String deviceId = null;
	private ParcelReceiver receiver;
	private State last;
	
	public StateCache(String id, String deviceId, ParcelReceiver receiver) {
		super(id);
		this.deviceId = deviceId;
		this.receiver = receiver;
		
		DCMThreadPool.getScheduledPool().schedule(this::query, DCMSettings.STARTUP_DELAY, TimeUnit.SECONDS);
		
	}
	
	public StateCache(String deviceId, ParcelReceiver receiver) {
		super();
		this.deviceId = deviceId;
		this.receiver = receiver;
	}
	
	@Override
	public void publish(Parcel<?> parcel) {
		receiver.receive(parcel);
	}

	@Override
	public void receive(Parcel<?> parcel) {
		last = (State) parcel.getPayload();
	}

	
	
	/**
	 * Queries the Device being cached, asking it to broadcast it's state
	 */
	public void query() {
		publish(new IParcel<>(getId(), deviceId, new StateQuery()));
	}
	
	public State get() {
		return last;
	}

	
	
}
