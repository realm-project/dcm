package net.realmproject.dcm.parcel.node;


import java.util.function.Function;
import java.util.function.Predicate;

import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.util.DCMUtil;


public class IParcelNode implements ParcelNode {

	private String id = DCMUtil.generateId();
    

    public IParcelNode(String id) {
        super();
        this.id = id;
    }
    
    public IParcelNode() {
        super();
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