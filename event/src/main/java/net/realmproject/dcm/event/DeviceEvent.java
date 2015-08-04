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
import net.realmproject.dcm.event.identity.SourceIdentity;
import net.realmproject.dcm.event.identity.TargetIdentity;


public interface DeviceEvent extends Serializable, Payload<Serializable>, SourceIdentity, TargetIdentity {

    /**
     * Gets the type of this event
     * 
     * @return the type of this event
     */
    DeviceEventType getDeviceEventType();

    /**
     * Sets the type of this event
     * 
     * @param type
     *            the new type of this event
     */
    void setDeviceEventType(DeviceEventType type);

    /**
     * Gets the time that this event was published (according to the computer
     * which published it)
     * 
     * @return date of publication
     */
    Date getTimestamp();

    /**
     * Sets the time that this event was published (according to the computer
     * which published it).
     * 
     * @param timestamp
     *            the new timestamp for this event
     */
    void setTimestamp(Date timestamp);

    /**
     * Gets the zone this event was published in
     * 
     * @return zone of origin
     */
    String getZone();

    /**
     * The zone property is an arbitrary string representing what part of a
     * larger network of devices the event originated from, and is usually not
     * set by the publishing device, but by a component like a
     * {@link DeviceEventBus}
     * 
     * @param zone
     *            the zone this event originates from
     */
    void setZone(String zone);

    /**
     * Private events are events which are not meant to be propagated beyond the
     * zone they were emitted in. {@link DeviceEventBus} implementations should
     * not broadcast private events from other zones.
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

    default DeviceEvent sourceId(String sourceId) {
        setSourceId(sourceId);
        return this;
    }

    default DeviceEvent targetId(String targetId) {
        setTargetId(targetId);
        return this;
    }

    default DeviceEvent payload(Serializable payload) {
        setPayload(payload);
        return this;
    }

    default DeviceEvent timestamp(Date timestamp) {
        setTimestamp(timestamp);
        return this;
    }

    default DeviceEvent type(DeviceEventType type) {
        setDeviceEventType(type);
        return this;
    }

}