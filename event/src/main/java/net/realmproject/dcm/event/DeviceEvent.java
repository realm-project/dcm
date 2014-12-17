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

package net.realmproject.dcm.event;


import java.io.Serializable;
import java.util.Date;

import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.messaging.DeviceMessageType;


public interface DeviceEvent {

    /**
     * Gets the ID of the device which originally published this event
     * 
     * @return id of the originating device
     */
    String getDeviceId();

    /**
     * Gets the type of this event
     * 
     * @return the type of this event
     */
    DeviceMessageType getDeviceMessageType();

    /**
     * Gets the payload of this event
     * 
     * @return The payload of this event
     */
    Serializable getValue();

    /**
     * Gets the time that this event was published (according to the computer
     * which published it)
     * 
     * @return date of publication
     */
    Date getTimestamp();

    /**
     * Gets the region this event was published in
     * 
     * @return region of origin
     */
    String getRegion();

    /**
     * The region property is an arbitrary string representing what part of a
     * larger network of devices the event originated from, and is usually not
     * set by the publishing device, but by a component like a
     * {@link DeviceEventBus}
     * 
     * @param region
     *            the region this event originates from
     * */
    void setRegion(String region);

    /**
     * Private events are events which are not meant to be propagated beyond the
     * region they were emitted in. {@link DeviceEventBus} implementations
     * should not broadcast private events from other regions.
     * 
     * @return true if this event is private, false otherwise.
     */
    boolean isPrivateEvent();

    /**
     * Sets this event as private.
     * 
     * @param privateEvent
     *            indicates if this event is private.
     */
    void setPrivateEvent(boolean privateEvent);

}