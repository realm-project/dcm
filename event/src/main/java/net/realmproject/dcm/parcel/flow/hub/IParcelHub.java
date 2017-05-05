/**
 * Copyright 2014 Realm Project
 * 
 * This file is part of the Device Control Module (DCM).
 * 
 * DCM is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DCM is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DCM. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package net.realmproject.dcm.parcel.flow.hub;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.realmproject.dcm.parcel.Logging;
import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.flow.link.ParcelLink;
import net.realmproject.dcm.parcel.node.IParcelNode;
import net.realmproject.dcm.parcel.node.receiver.ParcelReceiver;
import net.realmproject.dcm.util.DCMInterrupt;
import net.realmproject.dcm.util.DCMThreadPool;


/**
 * Implementation of ParcelHub. Broadcasting parcels is done on a single
 * (separate) thread in order of receipt.
 * 
 * @author NAS
 *
 */

public class IParcelHub extends IParcelNode implements ParcelLink, ParcelHub, Logging {

	public class Subscription {
		
		public ParcelReceiver receiver;
		public Predicate<Parcel<?>> filter;
		
		public Subscription(ParcelReceiver receiver, Predicate<Parcel<?>> filter) {
			this.receiver = receiver;
			this.filter = filter;
		}
		
	}

	
    protected List<Subscription> subscribers = new ArrayList<>();
    private BlockingQueue<Parcel<?>> parcelqueue = new LinkedBlockingQueue<>(1000);
    private String zone = "";
    private final Log log = LogFactory.getLog(getClass());

    public IParcelHub() {
        this("");
    }

    public IParcelHub(String zone) {
        this.zone = zone;
        DCMThreadPool.getPool().submit(() -> {
            Parcel<?> parcel = null;
            while (true) {
                try {
                    getLog().trace("ParcelHub " + IParcelHub.this.getId() + " Transforming Parcel " + parcel);
                    parcel = transform(parcelqueue.take());
                    getLog().trace("ParcelHub " + IParcelHub.this.getId() + " Broadcasting Parcel " + parcel);
                    broadcast(parcel);
                    getLog().trace("ParcelHub " + IParcelHub.this.getId() + " Finished Parcel " + parcel);
                }
                catch (InterruptedException e) {
                    getLog().warn("ParcelHub " + IParcelHub.this.getId() + " thread has been interrupted.");
                    getLog().trace("ParcelHub Thread Interrupted", e);
                    Thread.currentThread().interrupt();
                    return;
                }
                catch (Exception e) {
                    // the parcelhub thread cannot die, any exceptions which
                    // remain uncaught at this point should be logged, but
                    // discarded
                    getLog().error(parcel, e);
                }
            }
        });

    }

    protected synchronized void broadcast(Parcel<?> parcel) {
        for (Subscription subscriber : new ArrayList<>(subscribers)) {
        	if (subscriber.filter.test(parcel)) {
                // deepCopy to make sure that neither parcel settings nor the payload itself are mutated by separate next hops
        		DCMInterrupt.handle(() -> subscriber.receiver.receive(parcel.deepCopy()), e -> getLog().error(parcel, e));
        	}
        }
    }

    @Override
    public void receive(Parcel<?> parcel) {
        if (parcel == null) { return; }
        if (parcel.getRoute().contains(getId())) { return; } // cycle detection
        parcel.getRoute().add(getId());

        // only label a parcel with our zone if it hasn't already been set.
        // Relabelling of parcels should be a conscious choice of a relay
        if (parcel.getZone() == null) {
            parcel.setZone(getZone());
        }

        boolean isPrivate = parcel.isLocal();
        boolean sameZone = getZone() != null && getZone().equals(parcel.getZone());

        // private parcels from other zones will not be propagated
        if (isPrivate && !sameZone) { return; }

        // allow the bus to filter parcels
        if (!filter(parcel)) { return; }

        if (!parcelqueue.offer(parcel)) {
            getLog().error("Dropped parcel " + parcel + " because of full parcel queue");
        }

    }

    @Override
    public  void subscribe(ParcelReceiver subscriber) {
        subscribe(null, subscriber);
    }

    @Override
    public synchronized void subscribe(Predicate<Parcel<?>> filter, ParcelReceiver subscriber) {
        subscribers.add(new Subscription(subscriber, filter));
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public Log getLog() {
        return log;
    }

}


