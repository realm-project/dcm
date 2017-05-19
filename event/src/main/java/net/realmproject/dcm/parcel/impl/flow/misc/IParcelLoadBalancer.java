package net.realmproject.dcm.parcel.impl.flow.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;

public class IParcelLoadBalancer extends IParcelNode implements ParcelReceiver, ParcelSender  {

	private int nextIndex = 0;
	protected List<ParcelReceiver> receivers = new ArrayList<>();
	
	public IParcelLoadBalancer(ParcelReceiver... receivers) {
		this(Arrays.asList(receivers));
	}
	
	public IParcelLoadBalancer(List<ParcelReceiver> receivers) {
		this.receivers.addAll(receivers);
	}


	protected ParcelReceiver getReceiver(Parcel<?> parcel) {
		ParcelReceiver receiver = receivers.get(nextIndex++);
		if (nextIndex >= receivers.size()) { nextIndex = 0; }
		return receiver;
	}
	
	@Override
	public void send(Parcel<?> parcel) {
		getReceiver(parcel).receive(parcel);
		
	}

	@Override
	public void receive(Parcel<?> parcel) {
		send(parcel);
	}

	public List<ParcelReceiver> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<? extends ParcelReceiver> receivers) {
		this.receivers.clear();
		this.receivers.addAll(receivers);
	}
	
	public void setReceivers(ParcelReceiver... receivers) {
		setReceivers(Arrays.asList(receivers));
	}
	
}
