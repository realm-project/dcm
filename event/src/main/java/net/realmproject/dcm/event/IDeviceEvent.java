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
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.util.DCMUtil;


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
    private long timestamp = System.currentTimeMillis();

    /**
     * The location (event bus) that this event was issued. Larger device
     * networks can be composed of multiple event busses, bridged over a message
     * queuing system. The zone field identifies which event bus the event is
     * from, and allows events to be filtered by zone. See
     * {@link DeviceEventBus}
     */
    private String zone = null;
    private boolean privateEvent = false;
    private List<String> route = new LinkedList<>();
    
    private String id = DCMUtil.generateId();

    /**************************************************************************/

    public IDeviceEvent() {
        this(null, null, null, null, System.currentTimeMillis());
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
        this(type, sourceId, null, null, System.currentTimeMillis());
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
        this(type, sourceId, targetId, value, System.currentTimeMillis());
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
    public IDeviceEvent(DeviceEventType type, String sourceId, String targetId, Serializable value, long timestamp) {
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.type = type;
        this.payload = deepCopy(value);
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
        this.payload = deepCopy(payload);
    }

    @Override
    public long getTimestamp() {
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
    public boolean isPrivate() {
        return privateEvent;
    }

    @Override
    public void setPrivate(boolean privateEvent) {
        this.privateEvent = privateEvent;
    }

    public DeviceEvent deepCopy() {
        return (DeviceEvent) deepCopy(this);
    }

    public static Serializable deepCopy(Serializable input) {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(input);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Serializable) ois.readObject();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DeviceEvent shallowCopy() {
        IDeviceEvent copy = new IDeviceEvent();
        copy.timestamp = timestamp;
        copy.sourceId = sourceId;
        copy.targetId = targetId;
        copy.type = type;
        copy.payload = payload;
        copy.zone = zone;
        copy.privateEvent = privateEvent;
        copy.id = id;

        // copy the elements of the route stack, rather than the stack itself
        copy.route.addAll(route);

        return copy;
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
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public void setDeviceEventType(DeviceEventType type) {
        this.type = type;
    }

    @Override
    public List<String> getRoute() {
        return route;
    }

    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("IDeviceEvent[Type ");
        sb.append(getDeviceEventType());
        sb.append(" | From ");
        sb.append(getSourceId());

        if (getTargetId() != null) {
            sb.append(" To ");
            sb.append(getSourceId());
        }

        sb.append(" | Time ");
        sb.append(getTimestamp());

        sb.append(" | Via ");
        sb.append(getRoute().stream().collect(Collectors.joining(">")));

        sb.append("]");

        return sb.toString();

    }

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

}
