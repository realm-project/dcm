package net.realmproject.dcm.parcel.node.hub;


import net.realmproject.dcm.parcel.node.IParcelNode;
import net.realmproject.dcm.parcel.node.link.IParcelLink;
import net.realmproject.dcm.parcel.node.link.ParcelLink;


/**
 * A Bridge is a two-way {@link ParcelLink} between two
 * {@link ParcelHub}s
 * 
 * @author NAS
 *
 */

public class IParcelBridge extends IParcelNode {

	private ParcelLink to1, to2;
	
    public IParcelBridge(ParcelHub bus1, ParcelHub bus2) {

    	to1 = new IParcelLink(bus1);
    	to2 = new IParcelLink(bus2);
    	
    	to1.setId(getId());
    	to2.setId(getId());
    	
    	bus1.subscribe(to2);
    	bus2.subscribe(to1);
    	
    }
    
    @Override
    public void setId(String id) {
    	super.setId(id);
    	to1.setId(id);
    	to2.setId(id);
    }
    

}
