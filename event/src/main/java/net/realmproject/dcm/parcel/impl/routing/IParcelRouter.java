package net.realmproject.dcm.parcel.impl.routing;

import java.util.ArrayList;
import java.util.function.Predicate;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.routing.ParcelRouter;
import net.realmproject.dcm.parcel.core.routing.Route;
import net.realmproject.dcm.parcel.core.routing.RoutingTable;
import net.realmproject.dcm.parcel.impl.hub.IParcelHub;
import net.realmproject.dcm.parcel.impl.routing.routingtable.AutoRoutingTable;
import net.realmproject.dcm.parcel.impl.routing.routingtable.IAutoRoutingTable;
import net.realmproject.dcm.util.DCMInterrupt;

public class IParcelRouter extends IParcelHub implements ParcelRouter {

	private AutoRoutingTable routes = new IAutoRoutingTable(this);


    @Override
    public synchronized void subscribe(Predicate<Parcel<?>> filter, ParcelReceiver subscriber) {
        subscribers.add(new Subscription(subscriber, filter));
        routes.addParcelReceiver(subscriber);
    }
	
		
    public synchronized void send(Parcel<?> parcel) {
    	
    	Route nextHop = routes.nextHop(parcel.getTargetId());
    	    	
    	System.out.println("ID: " + getId() + "\nTarget: " + parcel.getTargetId() + "\nRoutes:\n" + routes + "\n******");
    	System.out.println("Next Hop: " + (nextHop != null ? nextHop.getNextHop() : "null"));
    	
    	
    	
        for (Subscription subscriber : new ArrayList<>(subscribers)) {
        	
        	//If there is a next hop defined, then only "broadcast" to the next hop
        	if (nextHop != null && !nextHop.getNextHop().equals(subscriber.receiver.getId())) { 
        		System.out.println("Skipping " + subscriber.receiver.getId());
        		continue;
        	}
        	
        	if (subscriber.filter == null || subscriber.filter.test(parcel)) {
        		
                // deepCopy to make sure that neither parcel settings nor the payload itself are mutated by separate next hops
        		DCMInterrupt.handle(() -> subscriber.receiver.receive(parcel.deepCopy()), e -> getLog().error(parcel, e));
        	}
        }
        
        System.out.println("*****************");
    }
	
	@Override
	public RoutingTable getRoutes() {	
		return routes;
	}

}
