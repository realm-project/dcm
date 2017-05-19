package net.realmproject.dcm.parcel.impl.flow.routing.routingtable.linkable;

import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;
import net.realmproject.dcm.parcel.impl.flow.routing.routingtable.IRoutingTable;
import net.realmproject.dcm.util.DCMSettings;
import net.realmproject.dcm.util.DCMThreadPool;

public class LinkableRoutingTable extends IRoutingTable {

	private ParcelSender owner;
	
	public LinkableRoutingTable(ParcelSender owner) {
		super(owner);
		this.owner = owner;
		DCMThreadPool.getScheduledPool().scheduleAtFixedRate(this::poll, DCMSettings.STARTUP_DELAY, 1, TimeUnit.SECONDS);
	}

	private void poll() {
		addLocal(super.owner.getId());
		trim();
		for (ParcelReceiver receiver : owner.getLinks()) {
			integrate(receiver);
		}
	}
	
}
