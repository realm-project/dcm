package net.realmproject.dcm.parcel.impl.branch;

import java.util.LinkedHashMap;
import java.util.Map;

import net.realmproject.dcm.parcel.core.Logging;
import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.branch.ParcelBranch;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;

public abstract class AbstractParcelBranch extends IParcelNode implements ParcelBranch, Logging {

	protected Map<String, ParcelReceiver> receivers = new LinkedHashMap<>();
	
	public AbstractParcelBranch(Map<String, ParcelReceiver> receivers) {
		this.receivers.putAll(receivers);
	}

	
	@Override
	public final void receive(Parcel<?> parcel) {
		send(parcel);
	}


	@Override
	public final void send(Parcel<?> parcel) {
		if (!parcel.visit(getId())) { return; } //cycle detection
        String branch = getBranch(parcel);
        if (receivers.keySet().contains(branch)) {
        	receivers.get(branch).receive(parcel);
        } else {
        	onInvalidBranch(parcel, branch);
        }
	}
	
	protected abstract String getBranch(Parcel<?> parcel);
	
	protected void onInvalidBranch(Parcel<?> parcel, String branch) {
		getLog().error("Branch " + branch + " not found", new RuntimeException("Invalid Branch"));
		 
	}
	
}
