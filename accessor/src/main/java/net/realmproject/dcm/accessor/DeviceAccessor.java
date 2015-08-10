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

package net.realmproject.dcm.accessor;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import net.realmproject.dcm.event.identity.Identity;
import net.realmproject.dcm.event.identity.TargetIdentity;
import net.realmproject.dcm.event.publisher.DeviceEventPublisher;


/**
 * DeviceAccessors track the state of a Device and allow indirect interaction
 * with it. This can be used to reduce latency for Device state lookups or for
 * isolating a Device from a high volume of queries. A listener interface
 * provides push notification of updates from the Device.
 * 
 * @author NAS
 *
 * @param <T>
 */
public interface DeviceAccessor<T extends Serializable> extends Identity, TargetIdentity, DeviceEventPublisher {

    /**
     * Sends a MESSAGE event with the given payload
     * 
     * @param input
     *            the payload to include with the event
     */
    void sendMessage(Serializable input);

    /**
     * Sends a VALUE_SET event with the given payload
     * 
     * @param input
     *            the payload to include with the event
     */
    void sendValueSet(Serializable input);

    /**
     * Gets the state of the backing device as represented by the most recently
     * received VALUE_CHANGED event from a backing device.
     * 
     * @return the state of a Device
     */
    T getState();

    /**
     * Gets the timestamp of the most recently received event from a backing
     * device.
     * 
     * @return the timestamp of the most recent event
     */
    Date getTimestamp();

    /**
     * Asynchronously emits a VALUE_GET event which may trigger a VALUE_CHANGED
     * event from a backing device.
     */
    void sendValueGet();

    List<Consumer<T>> getListeners();

    void setListeners(List<Consumer<T>> listeners);

}
