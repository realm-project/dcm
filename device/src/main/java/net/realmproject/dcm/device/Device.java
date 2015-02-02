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

package net.realmproject.dcm.device;


import java.io.Serializable;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.bus.IDeviceEventBusSender;
import net.realmproject.dcm.event.filter.composite.BooleanAndFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.PingFilter;
import net.realmproject.dcm.event.filter.deviceid.DeviceIDWhitelistFilter;


public abstract class Device extends IDeviceEventBusSender {

    private String id = null;

    public Device(String id, DeviceEventBus bus) {
        super(bus);
        this.id = id;

        // Respond to Pings
        Predicate<DeviceEvent> filter = new BooleanAndFilter(new PingFilter(), new DeviceIDWhitelistFilter(id));
        bus.subscribe(event -> send(new IDeviceEvent(DeviceEventType.PONG, id, event.getValue())), filter);
    }

    /**
     * Gets the id of this device
     * 
     * @return the id of this device
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the current value of this device
     * 
     * @return the value of this device
     */
    public abstract Object getValue();

    /**
     * Sets the value of this device. Users should not expect that a call to
     * setValue(X) followed by a call to getValue() will return X. Due to the
     * nature of physical devices, it may take some time for the device to reach
     * the desired state (if it ever does). <br>
     * <br>
     * It is also possible for a device to accept values which act as deltas or
     * commands, in which case the result of getValue should logically match up
     * with the requested action, but may not be an equivalent value or even a
     * similar data structure.
     * 
     * @param val
     *            The value to set this device to
     */
    public abstract void setValue(Object val);

    /**
     * Publish the state of this Device as a {@link DeviceEvent}
     */
    public void publish() {
        send(new IDeviceEvent(DeviceEventType.VALUE_CHANGED, id, (Serializable) getValue()));
    }
}
