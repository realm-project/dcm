/*
 * Copyright (c) Canadian Light Source, Inc. All rights reserved. - see
 * license.txt for details.
 * 
 * Description: Device class.
 */
package net.realmproject.dcm.device;


import java.io.Serializable;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.bus.IDeviceEventBusSender;
import net.realmproject.dcm.messaging.DeviceMessageType;


public abstract class Device extends IDeviceEventBusSender {

    private String id = null;

    public Device(String id, DeviceEventBus bus) {
        super(bus);
        this.id = id;
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
     * Gets the id of this device
     * 
     * @return the id of this device
     */
    public String getId() {
        return id;
    }

    /**
     * Publish the state of this Device as a {@link DeviceEvent}
     */
    public void publish() {
        send(new DeviceEvent(DeviceMessageType.VALUE_CHANGED, id, (Serializable) getValue()));
    }
}
