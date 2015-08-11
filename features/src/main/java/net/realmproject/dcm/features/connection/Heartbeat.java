package net.realmproject.dcm.features.connection;


import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.util.DCMThreadPool;


public interface Heartbeat extends Connection {

    boolean isHeartbeatStale();

    default ScheduledFuture<?> initHeartbeat(int interval) {
        initConnection();
        return DCMThreadPool.getScheduledPool().scheduleAtFixedRate(this::heartbeat, interval, interval, TimeUnit.SECONDS);
    }

    default void heartbeat() {
        synchronized (this) {
            if (!isHeartbeatStale()) { return; }
            getLog().info("Device " + getId() + " appears to be disconnected. Will attempt to reconnect.");
            disconnected(new RuntimeException("Heartbeat failure for " + getId()));
        }

    }

}
