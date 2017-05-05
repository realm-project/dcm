package net.realmproject.dcm.parcel.flow.structure;

import java.util.HashMap;
import java.util.Map;

import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.flow.hub.IParcelHub;
import net.realmproject.dcm.parcel.flow.link.IParcelLink;
import net.realmproject.dcm.parcel.flow.link.ParcelLink;
import net.realmproject.dcm.parcel.node.IParcelNode;
import net.realmproject.dcm.parcel.node.receiver.ParcelReceiver;

public class IParcelBranch extends IParcelNode implements ParcelLink {

	private Map<String, ParcelReceiver> receivers = new HashMap<>();
	
	public IParcelBranch(Map<String, ParcelReceiver> receivers) {
		this.receivers = receivers;
	}
	
	public String getBranch(Parcel<?> parcel) {
		return parcel.getName();
	}
	
    @Override
    public void receive(Parcel<?> parcel) {
        if (!filter(parcel)) { return; }
        if (!parcel.visit(getId())) { return; } //cycle detection
        String branchName = getBranch(parcel);
        if (receivers.keySet().contains(branchName)) {
        	receivers.get(branchName).receive(transform(parcel));
        }
    }
	
}
