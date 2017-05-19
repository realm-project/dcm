package net.realmproject.dcm.parcel.impl.flow.routing.routingtable.receivertracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.parcel.core.Identity;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.impl.flow.routing.routingtable.IRoutingTable;
import net.realmproject.dcm.util.DCMSettings;
import net.realmproject.dcm.util.DCMThreadPool;

public class IReceiverTrackingRoutingTable extends IRoutingTable implements ReceiverTrackingRoutingTable {

	private List<ParcelReceiver> receivers = new ArrayList<>();
	
	public IReceiverTrackingRoutingTable(Identity owner) {
		super(owner);
		DCMThreadPool.getScheduledPool().scheduleAtFixedRate(this::poll, DCMSettings.STARTUP_DELAY, 1, TimeUnit.SECONDS);
	}
	
	public void poll() {
		addLocal(super.owner.getId());
		trim();
		for (ParcelReceiver receiver : receivers) {
			integrate(receiver);
		}
	}
	
	@Override
	public void addParcelReceiver(ParcelReceiver receiver) {
		receivers.add(receiver);
	}

	@Override
	public void removeParcelReceiver(ParcelReceiver receiver) {
		receivers.remove(receiver);
	}

	@Override
	public void clearParcelReceivers() {
		receivers.clear();
	}

	@Override
	public void setParcelReceivers(Collection<ParcelReceiver> receivers) {
		receivers.clear();
		receivers.addAll(receivers);
	}

	
	
}
