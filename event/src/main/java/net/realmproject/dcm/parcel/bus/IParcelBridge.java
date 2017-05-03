package net.realmproject.dcm.parcel.bus;


import net.realmproject.dcm.parcel.IParcelNode;
import net.realmproject.dcm.parcel.receiver.ParcelReceiver;
import net.realmproject.dcm.parcel.relay.IParcelRelay;
import net.realmproject.dcm.parcel.relay.ParcelRelay;
import net.realmproject.dcm.parcel.router.IRoutingParcelRelay;


/**
 * A Bridge is a two-way {@link ParcelRelay} between two
 * {@link ParcelHub}
 * 
 * @author NAS
 *
 */

public class IParcelBridge extends IParcelNode {

	private ParcelReceiver to1, to2;
	
    public IParcelBridge(ParcelHub bus1, ParcelHub bus2) {

    	to1 = new IParcelRelay(bus1);
    	to2 = new IParcelRelay(bus2);
    	
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
