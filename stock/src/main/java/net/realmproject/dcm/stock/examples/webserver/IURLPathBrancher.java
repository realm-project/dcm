package net.realmproject.dcm.stock.examples.webserver;

import java.util.Map;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.parcel.core.metadata.ParcelNodeType;
import net.realmproject.dcm.parcel.impl.flow.branch.AbstractParcelBranch;

@ParcelMetadata(name="URL Path Brancher", type=ParcelNodeType.BRANCH)
public class IURLPathBrancher extends AbstractParcelBranch {

	public IURLPathBrancher() {
		super();
	}
	
	public IURLPathBrancher(Map<String, ParcelReceiver> receivers) {
		super(receivers);
	}

	protected String getBranch(Parcel<?> p) {
		WebContext cx = (WebContext) p.getPayload();
		return cx.traversePath();
	}
	


	

}
