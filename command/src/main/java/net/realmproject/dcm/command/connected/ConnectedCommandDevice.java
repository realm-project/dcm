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

package net.realmproject.dcm.command.connected;


import net.realmproject.dcm.command.CommandDevice;
import net.realmproject.dcm.command.DeviceState;
import net.realmproject.dcm.event.bus.DeviceEventBus;


/**
 * Base class for {@link CommandDevice}s which maintain a persistent connection
 * to a device. Implementing the abstract methods of this class will allow it to
 * manage a disconnect/reconnect cycle automatically.
 * 
 * @author NAS
 */
public abstract class ConnectedCommandDevice<T extends DeviceState> extends CommandDevice<T> {

    protected ConnectedCommandDevice(String id, DeviceEventBus bus) {
        super(id, bus);
    }

    /**
     * Client code should call this method when the device loses it's
     * connection. This will begin the reconnection process.
     * 
     * @param exception
     *            The exception generated upon disconnection
     */
    protected final synchronized void disconnected(Exception exception) {

        onDisconnect(exception);
        doConnect();

    }

    private void doConnect() {
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
            e2.printStackTrace();
            onDisconnect(e2);
        }
    }

    /**
     * Client code should call this method to start the initial connection
     * process on device initialization. This will begin the connection process.
     */
    protected final synchronized void start() {
        doConnect();
    }

    /**
     * Not to be called by client code. Called whenever this device is
     * disconnected. This method should do whatever is necessary to connect the
     * device to its destination.
     * 
     * @throws Exception
     *             on error
     */
    protected abstract void connect() throws Exception;

    /**
     * Not to be called by client code. Called whenever this device is newly
     * connected. This method should do whatever setup is necessary to get the
     * device to a consistent connected state.
     * 
     * @throws Exception
     *             on error
     */
    protected abstract void onConnect() throws Exception;

    /**
     * Not to be called by client code. Called whenever this device is
     * disconnected. This method should do whatever cleanup is necessary to
     * return the device to a consistent disconnected state.
     * 
     * @param exception
     *            The exception generated upon disconnection
     */
    protected abstract void onDisconnect(Exception exception);

}
