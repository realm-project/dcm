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

package net.realmproject.dcm.event.publisher;


import java.io.Serializable;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.IDeviceEvent;


/**
 * A DeviceEventPublisher is anything which publishes events of its own.
 * 
 * @author NAS
 *
 */
public interface DeviceEventPublisher {

    /**
     * Sends the given {@link DeviceEvent}. If this component is not currently
     * sending events (eg stopSending() has been called) this event will be
     * discarded.
     * 
     * @param event
     *            the event to send
     */
    void publish(DeviceEvent event);

    /**
     * Convenience method for publish(DeviceEvent). Builds a {@link DeviceEvent}
     * from the given values and publishes it.
     * 
     * @param type
     *            the type of DeviceEvent
     * @param sourceId
     *            the id of the originating node
     * @param value
     *            the payload for this event. To set no payload, pass null
     * 
     */
    default void publish(DeviceEventType type, String sourceId, Serializable value) {
        publish(new IDeviceEvent(type, sourceId, null, value));
    }

    /**
     * Convenience method for publish(DeviceEvent). Builds a {@link DeviceEvent}
     * from the given values and publishes it.
     * 
     * @param type
     *            the type of DeviceEvent
     * @param sourceId
     *            the id of the originating node
     * @param targetId
     *            the id of the target node. To set no targetId, pass null
     * @param value
     *            the payload for this event. To set no payload, pass null
     * 
     */
    default void publish(DeviceEventType type, String sourceId, String targetId, Serializable value) {
        publish(new IDeviceEvent(type, sourceId, targetId, value));
    }

}
