package net.realmproject.dcm.parcel.impl.flow.routing;

import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.flow.routing.ParcelRouter;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;

public class IRoutingParcelBridge extends IParcelNode {

	private ParcelReceiver to1, to2;
	
	public IRoutingParcelBridge(ParcelRouter router1, ParcelRouter router2) {
		this(null, router1, router2);
	}
	
    public IRoutingParcelBridge(String id, ParcelRouter router1, ParcelRouter router2) {
    	
    	if (id != null) { setId(id); }
    	
    	to1 = new IRoutingParcelLink(router1) {
    		public String getId() {
    			return id + ":" + router2.getId() + "->" + router1.getId();
    		}
    	};
    	to2 = new IRoutingParcelLink(router2) {
    		public String getId() {
    			return id + ":" + router1.getId() + "->" + router2.getId();
    		}
    	};
    	    	
    	router1.link(to2);
        router2.link(to1);
        
    }   

	
}
