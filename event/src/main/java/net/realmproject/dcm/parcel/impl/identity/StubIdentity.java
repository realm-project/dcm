package net.realmproject.dcm.parcel.impl.identity;

import java.io.Serializable;

import net.realmproject.dcm.parcel.core.Identity;

public class StubIdentity implements Identity, Serializable {

	private String id;
	
	public StubIdentity(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	
}
