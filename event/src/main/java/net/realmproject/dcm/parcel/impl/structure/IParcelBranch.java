package net.realmproject.dcm.parcel.impl.structure;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;

public class IParcelBranch extends IParcelNode implements ParcelReceiver, ParcelSender {

	private Map<String, ParcelReceiver> receivers = new HashMap<>();
	private Function<Parcel<?>, String> branchResolver = Parcel::getName;
	
	public IParcelBranch(Map<String, ParcelReceiver> receivers) {
		this.receivers = receivers;
	}

	@Override
	public void send(Parcel<?> parcel) {
		if (!parcel.visit(getId())) { return; } //cycle detection
        String branchName = branchResolver.apply(parcel);
        if (receivers.keySet().contains(branchName)) {
        	receivers.get(branchName).receive(parcel);
        }
	}

	public Function<Parcel<?>, String> getBranchResolver() {
		return branchResolver;
	}

	public void setBranchResolver(Function<Parcel<?>, String> branchResolver) {
		this.branchResolver = branchResolver;
	}

	@Override
	public void receive(Parcel<?> parcel) {
		send(parcel);
	}
	
	
	
	
}
