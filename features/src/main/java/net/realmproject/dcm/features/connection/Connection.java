package net.realmproject.dcm.features.connection;


import net.realmproject.dcm.event.Logging;
import net.realmproject.dcm.event.identity.Identity;


/**
 * Functionality for implementations which maintain a persistent connection to a
 * device. Implementing the abstract methods of this class will allow it to
 * manage a disconnect/reconnect cycle automatically. call startConnection to
 * initialize
 * 
 * @author NAS
 */
public interface Connection extends Identity, Logging {

    /**
     * Client code should call this method to start the initial connection
     * process on device initialization. This will begin the connection process.
     */
    default void initConnection() {
        synchronized (this) {
            doConnect();
        }
    }

    /**
     * Not to be called by client code. Called whenever this device is
     * disconnected. This method wraps the connect method with logging, error
     * handling, and a call to onConnect after succeeding.
     */
    default void doConnect() {
        try {

            getLog().info("Device " + getId() + " (re)connecting...");
            connect();
            getLog().info("Device " + getId() + " (re)connected!");

            onConnect();
        }
        catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
        }
        catch (Exception e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Device " + getId() + " failed to connect!\n");
            Throwable ex = e2;
            while (ex != null) {
                sb.append("\t" + ex.getMessage() + "\n");
                sb.append("\t\t" + ex.getStackTrace()[0] + "\n");
                ex = ex.getCause();
            }
            getLog().error(sb.toString());
            getLog().debug("Device " + getId() + " stack trace: ", e2);
            onDisconnect(e2);
        }
    }

    /**
     * Not to be called by client code. Called whenever this device is
     * disconnected. This method should do whatever is necessary to connect the
     * device to its destination.
     * 
     * @throws Exception
     *             on error
     */
    void connect() throws Exception;

    /**
     * Not to be called by client code. Called whenever this device is newly
     * connected. This method should do whatever setup is necessary to get the
     * device to a consistent connected state.
     * 
     * @throws Exception
     *             on error
     */
    void onConnect() throws Exception;

    /**
     * Not to be called by client code. Called whenever this device is
     * disconnected. This method should do whatever cleanup is necessary to
     * return the device to a consistent disconnected state.
     * 
     * @param exception
     *            The exception generated upon disconnection
     */
    void onDisconnect(Exception exception);

    /**
     * Client code should call this method when the device loses it's
     * connection. This will begin the reconnection process.
     * 
     * @param exception
     *            The exception generated upon disconnection
     */
    default void disconnected(Exception exception) {
        synchronized (this) {
            onDisconnect(exception);
            doConnect();
        }
    }

}
