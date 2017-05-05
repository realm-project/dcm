package net.realmproject.dcm.parcel.flow.hub;


import net.realmproject.dcm.parcel.flow.link.IParcelLink;
import net.realmproject.dcm.parcel.flow.link.ParcelLink;
import net.realmproject.dcm.parcel.node.IParcelNode;


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
    	
    	bus1.subscribe(this::filter, to2);
    	bus2.subscribe(this::filter, to1);
    	
    }
    
    @Override
    public void setId(String id) {
    	super.setId(id);
    	to1.setId(id);
    	to2.setId(id);
    }
    

}
