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

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.Logging;
import net.realmproject.dcm.event.sender.AbstractDeviceEventSender;
import net.realmproject.dcm.util.DCMThreadPool;

/**
 * Implementation of DeviceEventBus. Broadcasting events is done on a single
 * (separate) thread in order of receipt.
 * 
 * @author NAS
 *
 */

public class IDeviceEventBus extends AbstractDeviceEventSender implements DeviceEventBus, Logging {

    private List<Consumer<DeviceEvent>> consumers = new ArrayList<>();
    private BlockingQueue<DeviceEvent> eventqueue = new LinkedBlockingQueue<>(1000);
    private String region = "";

    public IDeviceEventBus() {
        this("");
    }

    public IDeviceEventBus(String region) {
        this.region = region;
        startSending();
        DCMThreadPool.getPool().submit(new Runnable() {

            @Override
            public void run() {
                DeviceEvent event = null;
                while (true) {
                    try {
                        event = eventqueue.take();

                        // only label event with our region if it hasn't already
                        // been set. relabelling of events should be a conscious
                        // choice of a repeater
                        if (event.getRegion() == null) {
                            event.setRegion(getRegion());
                        }

                        synchronized (this) {
	                        for (Consumer<DeviceEvent> consumer : consumers) {
	                            consumer.accept(event);
	                        }
                        }
                    }
                    catch (InterruptedException e) {
                        getLog().warn("Event bus thread has been interrupted.", e);
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
            }
        });

    }

    @Override
    public synchronized boolean broadcast(DeviceEvent event) {
        if (event.isPrivateEvent() && !getRegion().equals(event.getRegion())) {
            // private events from other regions will not be propagated
            return false;
        }
        return send(event);
    }

    @Override
    public synchronized void subscribe(Consumer<DeviceEvent> subscriber) {
        consumers.add(subscriber);
    }

    public final void subscribe(Consumer<DeviceEvent> subscriber, Predicate<DeviceEvent> filter) {
        subscribe(event -> {
            if (filter.test(event)) {
                subscriber.accept(event);
            }
        });
    }

    @SafeVarargs
    public final void subscribe(Consumer<DeviceEvent> subscriber, Predicate<DeviceEvent>... filters) {
        subscribe(event -> {
            for (Predicate<DeviceEvent> filter : filters) {
                if (!filter.test(event)) { return; }
            }
            subscriber.accept(event);
        });
    }

    public String getRegion() {
        return region;
    }

    @Override
    protected boolean doSend(DeviceEvent event) {
        return eventqueue.offer(event);
    }

}
