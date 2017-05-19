package net.realmproject.dcm.parcel.impl.flow.branch;

import java.util.Map;
import java.util.function.Function;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.flow.branch.ParcelBranch;

public class IParcelBranch extends AbstractParcelBranch implements ParcelBranch {

	private Function<Parcel<?>, String> branchResolver = Parcel::getName;

	public IParcelBranch() {
		super();
	}
	
	public IParcelBranch(Map<String, ParcelReceiver> receivers) {
		super(receivers);
	}

	protected final String getBranch(Parcel<?> parcel) {
		return branchResolver.apply(parcel);
	}
	
	public Function<Parcel<?>, String> getBranchResolver() {
		return branchResolver;
	}

	public void setBranchResolver(Function<Parcel<?>, String> branchResolver) {
		this.branchResolver = branchResolver;
	}

	
	
}
