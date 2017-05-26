package net.realmproject.dcm.parcel.impl.node;


import net.realmproject.dcm.parcel.core.ParcelNode;
import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.parcel.core.metadata.ParcelNodeType;
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
	
	@Override
	public String toString() {
		return "<" + getId() + ">";
	}

}