package net.realmproject.dcm.parcel.impl.flow.routing;

import java.util.ArrayList;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.flow.routing.ParcelRouter;
import net.realmproject.dcm.parcel.core.flow.routing.Route;
import net.realmproject.dcm.parcel.core.flow.routing.RoutingTable;
import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.parcel.core.metadata.ParcelNodeType;
import net.realmproject.dcm.parcel.impl.flow.hub.IParcelHub;
import net.realmproject.dcm.parcel.impl.flow.routing.routingtable.linkable.LinkableRoutingTable;

@ParcelMetadata (name="Router", type=ParcelNodeType.HUB)
public class IParcelRouter extends IParcelHub implements ParcelRouter {

	private RoutingTable routes = new LinkableRoutingTable(this);


		
    public synchronized void send(Parcel<?> parcel) {
    	
    	Route nextHop = routes.routeTo(parcel.getTargetId());
    	    	    	
        for (ParcelReceiver receiver : new ArrayList<>(receivers)) {
        	
        	//If there is a next hop defined, and this isn't it, skip it
        	if (nextHop != null && !nextHop.getNextHop().equals(receiver.getId())) { 
        		continue;
        	}

        	send(parcel, receiver);
        }
        
    }
	
	@Override
	public RoutingTable getRoutes() {	
		return routes;
	}

}
