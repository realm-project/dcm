package net.realmproject.dcm.command.connected;


import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.command.CommandDevice;
import net.realmproject.dcm.command.DeviceState;
import net.realmproject.dcm.event.DCMThreadPool;
import net.realmproject.dcm.event.bus.DeviceEventBus;


/**
 * For {@link CommandDevice}s which maintain a persistent connection to a
 * device, but are unable to correctly detect a disconnection event.
 * Implementing the abstract methods of this class will allow it to manage a
 * disconnect/reconnect cycle automatically.
 * 
 * @author NAS
 */
public abstract class HeartbeatCommandDevice<T extends DeviceState> extends ConnectedCommandDevice<T> {

    boolean previouslyConnected = false;

    protected HeartbeatCommandDevice(String id, DeviceEventBus bus, int interval, boolean heartbeat) {
        super(id, bus);

        if (heartbeat) {
            DCMThreadPool.getPool().scheduleAtFixedRate(this::testDisconnected, interval, interval, TimeUnit.SECONDS);
        }
    }

    private synchronized void testDisconnected() {
        if (!isDisconnected()) { return; }

        if (!previouslyConnected) {
            start();
            previouslyConnected = true;
        } else {
            getLog().info("Device " + getId() + " appears to be disconnected. Will attempt to reconnect.");
            disconnected(new RuntimeException("Heartbeat failure for " + getId()));
        }

    }

    /**
     * Test to see if the device is disconnected.
     * 
     * @return true if the device is disconnected, false otherwise
     */
    public abstract boolean isDisconnected();

}
