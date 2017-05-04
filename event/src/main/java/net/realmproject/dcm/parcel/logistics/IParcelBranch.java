package net.realmproject.dcm.parcel.logistics;

import java.util.HashMap;
import java.util.Map;

import net.realmproject.dcm.parcel.IParcelNode;
import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.bus.IParcelHub;
import net.realmproject.dcm.parcel.receiver.ParcelReceiver;
import net.realmproject.dcm.parcel.relay.AbstractParcelRelay;
import net.realmproject.dcm.parcel.relay.IParcelRelay;
import net.realmproject.dcm.parcel.relay.ParcelRelay;

public class IParcelBranch extends AbstractParcelRelay implements ParcelRelay {

	private Map<String, ParcelReceiver> receivers = new HashMap<>();
	
	public IParcelBranch(Map<String, ParcelReceiver> receivers) {
		this.receivers = receivers;
	}
	
	public String getBranch(Parcel<?> parcel) {
		return parcel.getName();
	}
	
    @Override
    public void accept(Parcel<?> parcel) {
        if (!filter(parcel)) { return; }
        if (!parcel.visit(getId())) { return; } //cycle detection
        String branchName = getBranch(parcel);
        if (receivers.keySet().contains(branchName)) {
        	receivers.get(branchName).accept(transform(parcel));
        }
    }
	
}
