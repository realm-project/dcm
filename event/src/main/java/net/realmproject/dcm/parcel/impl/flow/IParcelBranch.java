package net.realmproject.dcm.parcel.impl.flow;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;

public class IParcelBranch extends IParcelNode implements ParcelReceiver, ParcelSender {

	protected Map<String, ParcelReceiver> receivers = new LinkedHashMap<>();
	private Function<Parcel<?>, String> branchResolver = Parcel::getName;
	
	public IParcelBranch(Map<String, ParcelReceiver> receivers) {
		this.receivers.putAll(receivers);
	}

	@Override
	public void send(Parcel<?> parcel) {
		if (!parcel.visit(getId())) { return; } //cycle detection
        String branchName = getBranch(parcel);
        if (receivers.keySet().contains(branchName)) {
        	receivers.get(branchName).receive(parcel);
        }
	}

	protected String getBranch(Parcel<?> parcel) {
		return branchResolver.apply(parcel);
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
