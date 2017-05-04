package net.realmproject.dcm.parcel.flow.router;

import net.realmproject.dcm.parcel.node.IParcelNode;
import net.realmproject.dcm.parcel.node.receiver.ParcelReceiver;

public class IRoutingParcelBridge extends IParcelNode {

	private ParcelReceiver to1, to2;
	
    public IRoutingParcelBridge(ParcelRouter router1, ParcelRouter router2) {
    	
    	to1 = new IRoutingParcelRelay(router1);
    	to2 = new IRoutingParcelRelay(router2);
    	
    	to1.setId(getId());
    	to2.setId(getId());
    	
    	router1.subscribe(this::filter, to2);
        router2.subscribe(this::filter, to1);
        
    }

    @Override
    public void setId(String id) {
    	super.setId(id);
    	to1.setId(id);
    	to2.setId(id);
    }
    

	
}
