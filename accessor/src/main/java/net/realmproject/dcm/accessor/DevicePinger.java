package net.realmproject.dcm.accessor;


import java.util.concurrent.Future;


public interface DevicePinger extends DeviceAccessor {

    /**
     * Performs a ping and returns immediately
     */
    void ping();

    /**
     * Performs a ping and waits for a matching pong signal
     * 
     * @param timeout
     *            How long to wait for a pong signal. A negative value indicates
     *            no timeout
     * @return A {@link Future} representing the length of time in milliseconds
     *         between ping and pong
     */
    Future<Long> pingAndWait(long timeout);

}
