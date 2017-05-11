package net.realmproject.dcm.parcel.core;

import java.util.List;

public interface ParcelPath {

	/**
	 * Retrieves the route this parcel has traveled
	 * 
	 * @return the stack of nodes this parcel has traversed
	 */
	public List<String> getPath();
	
	/**
	 * Records that this parcel has passed through this node. If this parcel has passed through this node before, returns false.
	 * @param id the Id of the node being visited
	 * @return False if this node has already been visitid by this (copy of) this parcel, True otherwise
	 */
	default boolean visit(String id) {
		if (hasVisited(id)) { return false; }
        getPath().add(id);
        return true;
	}
	
	default boolean hasVisited(String id) {
		return getPath().contains(id);
	}
	
	
}
