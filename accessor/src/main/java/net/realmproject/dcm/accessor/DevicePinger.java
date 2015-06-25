package net.realmproject.dcm.accessor;

public interface DevicePinger extends AccessorIdentity {

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
