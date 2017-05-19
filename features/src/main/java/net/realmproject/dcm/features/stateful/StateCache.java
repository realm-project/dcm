package net.realmproject.dcm.features.stateful;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelSender;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;
import net.realmproject.dcm.parcel.impl.parcel.IParcel;
import net.realmproject.dcm.util.DCMSettings;
import net.realmproject.dcm.util.DCMThreadPool;

public class StateCache extends IParcelNode implements ParcelReceiver, ParcelSender {

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
	public void send(Parcel<?> parcel) {
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
		send(new IParcel<>(getId(), deviceId, new StateQuery()));
	}
	
	public State get() {
		return last;
	}

	@Override
	public List<ParcelReceiver> getLinks() {
		return Collections.singletonList(receiver);
	}

	
	
}
