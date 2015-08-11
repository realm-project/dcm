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
import java.util.List;

import net.realmproject.dcm.event.bus.DeviceEventBus;


/**
 * A DeviceEvent repreents a message on the DCM event graph. It can any of the
 * types defined by {@link DeviceEventType}, and optionally contains a
 * {@link Serializable} payload.
 * 
 * @author NAS
 *
 */
public interface DeviceEvent extends Serializable {

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
     * Gets the current payload for this DeviceEvent.
     * 
     * @return The current payload. May be null.
     */
    <S extends Serializable> S getPayload();

    /**
     * Sets the payload for this DeviceEvent.
     * 
     * @param payload
     *            The new payload. May be null.
     */
    void setPayload(Serializable payload);

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
    boolean isPrivate();

    /**
     * Sets this event as private.
     * 
     * @param privateEvent
     *            indicates if this event is private.
     */
    void setPrivate(boolean privateEvent);

    /**
     * Fluent API convenience method. See setSourceId
     * 
     * @return This DeviceEvent
     */
    default DeviceEvent sourceId(String sourceId) {
        setSourceId(sourceId);
        return this;
    }

    /**
     * Fluent API convenience method. See setTargetId
     * 
     * @return This DeviceEvent
     */
    default DeviceEvent targetId(String targetId) {
        setTargetId(targetId);
        return this;
    }

    /**
     * Fluent API convenience method. See setPayload
     * 
     * @return This DeviceEvent
     */
    default DeviceEvent payload(Serializable payload) {
        setPayload(payload);
        return this;
    }

    /**
     * Fluent API convenience method. See setTimestamp
     * 
     * @return This DeviceEvent
     */
    default DeviceEvent timestamp(Date timestamp) {
        setTimestamp(timestamp);
        return this;
    }

    /**
     * Fluent API convenience method. See setDeviceEventType
     * 
     * @return This DeviceEvent
     */
    default DeviceEvent type(DeviceEventType type) {
        setDeviceEventType(type);
        return this;
    }

    default DeviceEvent zone(String zone) {
        setZone(zone);
        return this;
    }

    default DeviceEvent privateEvent(boolean privateEvent) {
        setPrivate(privateEvent);
        return this;
    }

    /**
     * Represents the id of the Device this event is intended for. Only messages
     * intended as point-to-point will require this field
     * 
     * @return the id of the target Device
     */
    String getTargetId();

    /**
     * Represents the id of the Device this event is intended for. Only messages
     * intended as point-to-point will require this field
     * 
     * @param target
     *            the new id of the target Device
     */
    void setTargetId(String target);

    /**
     * Represents the id of the Device which emitted this event. This field
     * should be set for all messages.
     * 
     * @return the id of the source Device
     */
    String getSourceId();

    /**
     * Represents the id of the Device which emitted this event. This field
     * should be set for all messages.
     * 
     * @param id
     *            the new id of the source Device
     */
    void setSourceId(String id);

    /**
     * Performs a deep copy of this DeviceEvent including making a copy of the
     * payload.
     * 
     * @return The deep copy of this DeviceEvent
     */
    public DeviceEvent deepCopy();

    /**
     * Performs a deep copy of this DeviceEvent without making a copy of the
     * payload.
     * 
     * @return The shallow copy of this DeviceEvent
     */
    public DeviceEvent shallowCopy();

    /**
     * Retrieves the route this event has traveled
     * 
     * @return the stack of nodes this event has traversed
     */
    public List<String> getRoute();

}