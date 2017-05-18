package net.realmproject.dcm.parcel.core.branch;

import net.realmproject.dcm.parcel.core.ParcelNode;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;

public interface ParcelBranch extends ParcelReceiver, ParcelSender, ParcelNode {

	void addBranch(String name, ParcelReceiver receiver);
	void removeBranch(String name);
	void clearBranches();
	
}
