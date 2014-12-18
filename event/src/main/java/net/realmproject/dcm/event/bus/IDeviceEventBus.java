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

import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.sender.AbstractDeviceEventSender;
import net.realmproject.dcm.util.DCMThreadPool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Implementation of DeviceEventBus. Broadcasting events is done on a single
 * (separate) thread in order of receipt.
 * 
 * @author NAS
 *
 */

public class IDeviceEventBus extends AbstractDeviceEventSender implements DeviceEventBus {

    private List<Consumer<IDeviceEvent>> consumers = new ArrayList<>();
    private BlockingQueue<IDeviceEvent> eventqueue = new LinkedBlockingQueue<>(1000);

    private String region = "";
    protected final Log log = LogFactory.getLog(getClass());

    public IDeviceEventBus() {
        this("");
    }

    public IDeviceEventBus(String region) {
        this.region = region;
        startSending();
        DCMThreadPool.getPool().submit(new Runnable() {

            @Override
            public void run() {
                IDeviceEvent event = null;
                while (true) {
                    try {
                        event = eventqueue.take();

                        // only label event with our region if it hasn't already
                        // been set. relabelling of events should be a conscious
                        // choice of a repeater
                        if (event.getRegion() == null) {
                            event.setRegion(getRegion());
                        }

                        for (Consumer<IDeviceEvent> consumer : consumers) {
                            consumer.accept(event);
                        }
                    }
                    catch (InterruptedException e) {
                        log.warn("Event bus thread has been interrupted.", e);
                        Thread.currentThread().interrupt();
                        return;
                    }
                    catch (Exception e) {
                        // the eventbus thread cannot die, any exceptions which
                        // remain uncaught at this point should be logged, but
                        // discarded
                        log.error(event, e);
                    }
                }
            }
        });

    }

    @Override
    public synchronized boolean broadcast(IDeviceEvent event) {
        if (event.isPrivateEvent() && !getRegion().equals(event.getRegion())) {
            // private events from other regions will not be propagated
            return false;
        }
        return send(event);
    }

    @Override
    public synchronized void subscribe(Consumer<IDeviceEvent> subscriber) {
        consumers.add(subscriber);
    }

    public final void subscribe(Consumer<IDeviceEvent> subscriber, Predicate<IDeviceEvent> filter) {
        subscribe(event -> {
            if (filter.test(event)) {
                subscriber.accept(event);
            }
        });
    }

    @SafeVarargs
    public final void subscribe(Consumer<IDeviceEvent> subscriber, Predicate<IDeviceEvent>... filters) {
        subscribe(event -> {
            for (Predicate<IDeviceEvent> filter : filters) {
                if (!filter.test(event)) { return; }
            }
            subscriber.accept(event);
        });
    }

    public String getRegion() {
        return region;
    }

    @Override
    protected boolean doSend(IDeviceEvent event) {
        return eventqueue.offer(event);
    }

}
