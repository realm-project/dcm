package net.realmproject.dcm.parcel.impl.routing;

import java.util.ArrayList;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.routing.ParcelRouter;
import net.realmproject.dcm.parcel.core.routing.Route;
import net.realmproject.dcm.parcel.core.routing.RoutingTable;
import net.realmproject.dcm.parcel.impl.hub.IParcelHub;
import net.realmproject.dcm.parcel.impl.routing.routingtable.AutoRoutingTable;
import net.realmproject.dcm.parcel.impl.routing.routingtable.IAutoRoutingTable;

public class IParcelRouter extends IParcelHub implements ParcelRouter {

	private AutoRoutingTable routes = new IAutoRoutingTable(this);


		
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
