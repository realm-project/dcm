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
