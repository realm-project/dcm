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

package net.realmproject.dcm.event.bus;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.Logging;
import net.realmproject.dcm.event.relay.AbstractDeviceEventRelay;
import net.realmproject.dcm.util.DCMInterrupt;
import net.realmproject.dcm.util.DCMThreadPool;


/**
 * Implementation of DeviceEventBus. Broadcasting events is done on a single
 * (separate) thread in order of receipt.
 * 
 * @author NAS
 *
 */

public class IDeviceEventBus extends AbstractDeviceEventRelay implements DeviceEventBus, Logging {

    private List<Consumer<DeviceEvent>> consumers = new ArrayList<>();
    private BlockingQueue<DeviceEvent> eventqueue = new LinkedBlockingQueue<>(1000);
    private String zone = "";
    private final Log log = LogFactory.getLog(getClass());

    public IDeviceEventBus() {
        this("");
    }

    public IDeviceEventBus(String zone) {
        this.zone = zone;
        DCMThreadPool.getPool().submit(() -> {
            DeviceEvent event = null;
            while (true) {
                try {
                    event = transform(eventqueue.take());
                    broadcast(event);
                }
                catch (InterruptedException e) {
                    getLog().warn("Event bus thread has been interrupted.");
                    Thread.currentThread().interrupt();
                    return;
                }
                catch (Exception e) {
                    // the eventbus thread cannot die, any exceptions which
                    // remain uncaught at this point should be logged, but
                    // discarded
                    getLog().error(event, e);
                }
            }
        });

    }

    private synchronized void broadcast(DeviceEvent event) {
        for (Consumer<DeviceEvent> consumer : new ArrayList<>(consumers)) {
            // shallow copy to ensure things like route are not clobbered by
            // different nodes further on.
            DCMInterrupt.handle(() -> consumer.accept(event.shallowCopy()), e -> getLog().error(event, e));
        }
    }

    @Override
    public synchronized void accept(DeviceEvent event) {
        if (event == null) { return; }
        if (event.getRoute().contains(getId())) { return; } // cycle detection
        event.getRoute().add(getId());

        // only label event with our zone if it hasn't already been set.
        // Relabelling of events should be a conscious choice of a relay
        if (event.getZone() == null) {
            event.setZone(getZone());
        }

        boolean isPrivate = event.isPrivate();
        boolean sameZone = getZone() != null && getZone().equals(event.getZone());

        // private events from other zones will not be propagated
        if (isPrivate && !sameZone) { return; }

        // allow the bus to filter events
        if (!filter(event)) { return; }

        eventqueue.offer(event);
    }

    @Override
    public synchronized void subscribe(Consumer<DeviceEvent> subscriber) {
        consumers.add(subscriber);
    }

    public final void subscribe(Predicate<DeviceEvent> filter, Consumer<DeviceEvent> subscriber) {
        subscribe(event -> {
            if (filter.test(event)) {
                subscriber.accept(event);
            }
        });
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
