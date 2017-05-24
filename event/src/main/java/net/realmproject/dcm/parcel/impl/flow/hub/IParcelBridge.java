package net.realmproject.dcm.parcel.impl.flow.hub;


import net.realmproject.dcm.parcel.core.flow.hub.ParcelHub;
import net.realmproject.dcm.parcel.core.flow.link.ParcelLink;
import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.parcel.core.metadata.ParcelNodeType;
import net.realmproject.dcm.parcel.impl.flow.link.IParcelLink;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;


/**
 * A Bridge is a two-way {@link ParcelLink} between two
 * {@link ParcelHub}s
 * 
 * @author NAS
 *
 */
@ParcelMetadata (name="Hub Bridge", type=ParcelNodeType.OTHER)
public class IParcelBridge extends IParcelNode {

	private ParcelLink to1, to2;
	
    public IParcelBridge(ParcelHub bus1, ParcelHub bus2) {

    	to1 = new IParcelLink();
    	to2 = new IParcelLink();
    	
    	to1.setId(getId());
    	to2.setId(getId());
    	
    	bus1.link(to2).link(bus2);
    	bus2.link(to1).link(bus1);
    	
    	
    }
    
    @Override
    public void setId(String id) {
    	super.setId(id);
    	to1.setId(id);
    	to2.setId(id);
    }
    

}
