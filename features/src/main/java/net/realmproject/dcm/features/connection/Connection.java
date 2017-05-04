package net.realmproject.dcm.features.connection;


import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.features.DCMState;
import net.realmproject.dcm.parcel.Logging;
import net.realmproject.dcm.parcel.node.identity.Identity;
import net.realmproject.dcm.util.DCMThreadPool;
import net.realmproject.dcm.util.backoff.BackoffGenerator;


/**
 * Functionality for implementations which maintain a persistent connection to a
 * device. Implementing the abstract methods of this class will allow it to
 * manage a disconnect/reconnect cycle automatically. call startConnection to
 * initialize
 * 
 * @author NAS
 */
public interface Connection extends Identity, Logging, DCMState {

    public enum State {
        CLOSED, OPEN, FAILED, RESTARTING
    }

    /**
     * Client code should call this method to start the initial connection
     * process on device initialization. This will begin the connection process.
     */
    default boolean connectionInitialize() {
        synchronized (this) {
            try {
                connectionOpen();
                setConnectionState(State.OPEN);
                BackoffGenerator backoff = getDCMState(Connection.class, "backoff", new BackoffGenerator());
                backoff.reset();
                return true;
            }
            catch (Exception e) {
                getLog().error("Connection Attempt Failed", e);
                setConnectionState(State.FAILED);
                connectionFailed(new RuntimeException("Encountered Exception Opening Connection"));
                return false;
            }
        }
    }

    /**
     * Client code should call this method to permanently close and stop the
     * connection
     */
    default void connectionTerminate() {
        synchronized (this) {
            try {
                this.connectionClose();
            }
            catch (Exception e) {
                getLog().error("Encountered Exception Closing Connection", e);
            }
            setConnectionState(State.CLOSED);
        }
    }

    /**
     * Client code should call this method when the device loses it's
     * connection. This will begin the reconnection process immediately.
     * 
     * @param exception
     *            The exception generated upon disconnection
     */
    default boolean connectionRestart() {
        synchronized (this) {
            connectionTerminate();
            return connectionInitialize();
        }
    }

    /**
     * Client code should call this method when the device loses it's
     * connection. This will begin the reconnection process asynchronously,
     * using a user-customizable backoff function to prevent spamming the target
     * 
     * @param exception
     * @return
     */
    default ScheduledFuture<?> connectionFailed(Exception exception) {
        synchronized (this) {
            getLog().error("Connection Failed", exception);
            setConnectionState(State.RESTARTING);

            // get the backoff generator and schedule a reconnection attempt for
            // later
            BackoffGenerator backoff = getDCMState(Connection.class, "backoff", new BackoffGenerator());
            return DCMThreadPool.getScheduledPool().schedule(() -> {
                synchronized (this) {
                    if (getConnectionState() == State.RESTARTING) {
                        connectionRestart();
                    }
                }
            } , backoff.getDelay(), TimeUnit.MILLISECONDS);
        }
    }

    /************************************************
     * User-Implemented Code
     ************************************************/

    /**
     * Not to be called by client code. Called whenever this connection is to be
     * opened. This method should do whatever is necessary to connect the device
     * to its destination.
     * 
     * @throws Exception
     *             on error
     */
    void connectionOpen() throws Exception;

    /**
     * Not to be called by client code. Called whenever this device is to be
     * disconnected. This method should do whatever is necessary to disconnect
     * the device from its destination.
     * 
     * @throws Exception
     *             on error
     */
    void connectionClose() throws Exception;

    Connection.State getConnectionState();

    void setConnectionState(Connection.State state);

    default void setConnectionBackoff(BackoffGenerator backoff) {
        setDCMState(Connection.class, "backoff", backoff);
    }

}
