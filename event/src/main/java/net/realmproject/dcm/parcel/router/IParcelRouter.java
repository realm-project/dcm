package net.realmproject.dcm.parcel.router;

import java.util.ArrayList;

import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.bus.IParcelHub;
import net.realmproject.dcm.parcel.receiver.ParcelReceiver;
import net.realmproject.dcm.util.DCMInterrupt;

public class IParcelRouter extends IParcelHub implements ParcelRouter {

	
    protected synchronized void broadcast(Parcel<?> parcel) {
    	
    	RoutingTable routes = getRoutes();
    	Route nextHop = routes.nextHop(parcel.getTargetId());
    	
    	System.out.println(getId());
    	System.out.println(parcel.getTargetId());
    	System.out.println(routes);
    	
    	
        for (Subscription subscriber : new ArrayList<>(subscribers)) {
        	
        	//If there is a next hop defined, then only "broadcast" to the next hop
        	if (nextHop != null && !nextHop.nextHop.equals(subscriber.receiver.getId())) { 
        		System.out.println("Skipping " + subscriber.receiver.getId());
        		continue;
        	}
        	
        	if (subscriber.filter == null || subscriber.filter.test(parcel)) {
                // deepCopy to make sure that neither parcel settings nor the payload itself are mutated by separate next hops
        		DCMInterrupt.handle(() -> subscriber.receiver.accept(parcel.deepCopy()), e -> getLog().error(parcel, e));
        	}
        }
        
        
        System.out.println("*****************");
    }
	
	@Override
	public RoutingTable getRoutes(String previousHop) {
		
		RoutingTable routes = new IRoutingTable();
		routes.addLocal(getId());

		
		for (IParcelHub.Subscription subscriber : new ArrayList<>(subscribers)) {
			ParcelReceiver receiver = subscriber.receiver;
			if (receiver.getId().equals(previousHop)) { continue; }
			
			RoutingTable otherRoutes;
			if (receiver instanceof Routing) {
				otherRoutes = ((Routing)receiver).getRoutes(getId());
			} else {
				//Try out best to capture any terminal nodes which don't support routing
				otherRoutes = new IRoutingTable();
				otherRoutes.addLocal(receiver.getId());
			}
			
			otherRoutes.hop(receiver.getId());
			routes.add(otherRoutes);
		}
		
		return routes;
		
	}

}
