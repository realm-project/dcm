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


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

import net.realmproject.dcm.event.bus.DeviceEventBus;


/**
 * 
 * A DeviceEvent consists of an id representing the device of publication, a
 * type representing the intent of the message, and an optional
 * {@link Serializable} payload. <br>
 * <br>
 * DeviceEvents also have timestamp and zone properties. The zone property is an
 * arbitrary string representing what part of a larger network of devices the
 * event originated from, and is usually not set by the publishing device, but
 * by a component like a {@link DeviceEventBus}
 * 
 * @author chabotd, NAS
 *
 */
public class IDeviceEvent implements DeviceEvent {

    private static final long serialVersionUID = 1L;

    /**
     * The source and target ids for this event
     */
    private String sourceId, targetId;

    /**
     * The type of event/message
     */
    private DeviceEventType type;

    /**
     * The value or payload of this event
     */
    private Serializable payload;

    /**
     * The time (as recorded by the computer of origin) that this event was
     * issued
     */
    private Date timestamp;

    /**
     * The location (event bus) that this event was issued. Larger device
     * networks can be composed of multiple event busses, bridged over a message
     * queuing system. The zone field identifies which event bus the event is
     * from, and allows events to be filtered by zone. See
     * {@link DeviceEventBus}
     */
    private String zone = null;
    private boolean privateEvent = false;

    /**************************************************************************/

    public IDeviceEvent() {
        this(null, null, null, null, new Date());
    }

    /**
     * Creates a new DeviceEvent with no payload and a current timestamp
     * 
     * @param type
     *            The type of this message
     * @param sourceId
     *            The id of the originating device
     */
    public IDeviceEvent(DeviceEventType type, String sourceId) {
        this(type, sourceId, null, null, new Date());
    }

    /**
     * Creates a new DeviceEvent with the given payload and timestamp
     * 
     * @param type
     *            The type of this message
     * @param sourceId
     *            The id of the originating device
     * @param targetId
     *            The id of the target device
     * @param value
     *            The payload for this event
     */
    public IDeviceEvent(DeviceEventType type, String sourceId, String targetId, Serializable value) {
        this(type, sourceId, targetId, value, new Date());
    }

    /**
     * Creates a new DeviceEvent with the given payload and timestamp
     * 
     * @param type
     *            The type of this message
     * @param sourceId
     *            The id of the originating device
     * @param targetId
     *            The id of the target device
     * @param value
     *            The payload for this event
     * @param timestamp
     *            The timestamp this event was issued at
     */
    public IDeviceEvent(DeviceEventType type, String sourceId, String targetId, Serializable value, Date timestamp) {
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.type = type;
        this.payload = value;
        this.timestamp = timestamp;
    }

    @Override
    public DeviceEventType getDeviceEventType() {
        return type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S extends Serializable> S getPayload() {
        return (S) payload;
    }

    @Override
    public void setPayload(Serializable payload) {
        this.payload = payload;
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String getZone() {
        return zone;
    }

    @Override
    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public boolean isPrivateEvent() {
        return privateEvent;
    }

    @Override
    public void setPrivateEvent(boolean privateEvent) {
        this.privateEvent = privateEvent;
    }

    @Override
    public String toString() {

        String str = "Event";

        if (type != null) {
            str += ":" + type.toString();
        }

        if (getSourceId() != null) {
            str += " from " + getSourceId();
        }

        if (getTargetId() != null) {
            str += " to " + getTargetId();
        }

        return str;
    }

    @Override
    public DeviceEvent deepCopy() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (DeviceEvent) ois.readObject();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSourceId() {
        return sourceId;
    }

    @Override
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public String getTargetId() {
        return targetId;
    }

    @Override
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    @Override
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public void setDeviceEventType(DeviceEventType type) {
        this.type = type;
    }

}
