package net.realmproject.dcm.stock.ping;

import net.realmproject.dcm.parcel.core.Identity;

public interface DevicePinger extends Identity {

    /**
     * Performs a ping and returns immediately
     */
    void ping();

    /**
     * Determines the latency as calculated by the last ping sent
     * 
     * @return latency time in miliseconds, -1 if no ping has yet succeeded.
     */
    long getLatency();

}
