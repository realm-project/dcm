package net.realmproject.dcm.parcel.impl.hub;


import net.realmproject.dcm.parcel.core.hub.ParcelHub;
import net.realmproject.dcm.parcel.core.link.ParcelLink;
import net.realmproject.dcm.parcel.impl.link.IParcelLink;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;


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

    	to1 = new IParcelLink();
    	to2 = new IParcelLink();
    	
    	to1.setId(getId());
    	to2.setId(getId());
    	
    	bus1.subscribe(to2).link(bus2);
    	bus2.subscribe(to1).link(bus1);
    	
    	
    }
    
    @Override
    public void setId(String id) {
    	super.setId(id);
    	to1.setId(id);
    	to2.setId(id);
    }
    

}
