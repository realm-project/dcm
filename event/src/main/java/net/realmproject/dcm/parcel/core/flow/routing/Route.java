package net.realmproject.dcm.parcel.core.flow.routing;

import java.io.Serializable;

import net.realmproject.dcm.parcel.core.ParcelPath;

public interface Route extends ParcelPath, Serializable {

	long getExpiry();
	boolean isExpired();
	void extendExpiry();

	String getNextHop();
	int getHopCount();
	String getTarget();

	boolean equals(Object other);

}