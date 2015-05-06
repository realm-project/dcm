package net.realmproject.dcm.command.connected;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.util.DCMThreadPool;

public interface Heartbeat extends Connected {

	boolean isHeartbeatStale();
	
	default ScheduledFuture<?> startHeartbeat(int interval) {
		startConnection();
		return DCMThreadPool.getPool().scheduleAtFixedRate(this::isHeartbeatStale, interval, interval, TimeUnit.SECONDS);
	}	
	
    default void heartbeat() {
    	synchronized(this){
	        if (!isHeartbeatStale()) { return; }
	        getLog().info("Device " + getId() + " appears to be disconnected. Will attempt to reconnect.");
	        disconnected(new RuntimeException("Heartbeat failure for " + getId()));
    	}

    }
	
}
