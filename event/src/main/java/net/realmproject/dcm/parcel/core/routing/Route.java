package net.realmproject.dcm.parcel.core.routing;

import net.realmproject.dcm.parcel.core.ParcelPath;

public interface Route extends ParcelPath {

	long getExpiry();
	boolean isExpired();
	void extendExpiry();

	String getNextHop();
	int getHopCount();
	String getTarget();

	boolean equals(Object other);

}