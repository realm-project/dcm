package net.realmproject.dcm.features.connection;


import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.features.DCMState;
import net.realmproject.dcm.util.DCMThreadPool;
import net.realmproject.dcm.util.backoff.BackoffGenerator;


public interface Heartbeat extends Connection, DCMState {

    boolean isHeartbeatStale();

    default void heartbeatInitialize(int interval) {
        connectionInitialize();
        ScheduledFuture<?> heartbeat = DCMThreadPool.getScheduledPool().scheduleAtFixedRate(this::heartbeatPoll,
                interval, interval, TimeUnit.SECONDS);
        setDCMState(Heartbeat.class, "heartbeat", heartbeat);
    }

    default void heartbeatTerminate() {
        ScheduledFuture<?> heartbeat = getDCMState(Heartbeat.class, "heartbeat", null);
        if (heartbeat != null) {
            heartbeat.cancel(false);
        }
        connectionTerminate();
    }

    default void heartbeatPoll() {
        synchronized (this) {
            // if the connection is already restarting, skip the heartbeat check
            if (getConnectionState() == State.RESTARTING) { return; }
            // if the heartbeat isn't stale, don't do anything
            if (!isHeartbeatStale()) { return; }
            heartbeatFailed();
        }
    }

    default void heartbeatFailed() {
        synchronized (this) {
            // set the connection state to restarting to indicate that there is
            // a restart op in progress
            setConnectionState(State.RESTARTING);
            getLog().info("Device " + getId() + " appears to be disconnected. Will attempt to reconnect.");

            // get the backoff generator and schedule a reconnection attempt for
            // later
            BackoffGenerator backoff = getDCMState(Heartbeat.class, "backoff", new BackoffGenerator());
            DCMThreadPool.getScheduledPool().schedule(() -> {
                synchronized (this) {
                    if (getConnectionState() == State.RESTARTING) {
                        connectionRestart();
                    }
                }
            } , backoff.getDelay(), TimeUnit.MILLISECONDS);

        }
    }

    default void setHeartbeatBackoff(BackoffGenerator backoff) {
        setDCMState(Heartbeat.class, "backoff", backoff);
    }

}
