package net.realmproject.dcm.parcel.impl.routing;

import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.routing.ParcelRouter;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;

public class IRoutingParcelBridge extends IParcelNode {

	private ParcelReceiver to1, to2;
	
	public IRoutingParcelBridge(ParcelRouter router1, ParcelRouter router2) {
		this(null, router1, router2);
	}
	
    public IRoutingParcelBridge(String id, ParcelRouter router1, ParcelRouter router2) {
    	
    	if (id != null) { setId(id); }
    	
    	to1 = new IRoutingParcelRelay(router1);
    	to2 = new IRoutingParcelRelay(router2);
    	
    	to1.setId(getId() + ":2->1");
    	to2.setId(getId() + ":1->2");
    	
    	router1.subscribe(to2);
        router2.subscribe(to1);
        
    }   

	
}
