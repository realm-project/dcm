/**
 * Copyright (c) Canadian Light Source, Inc. All rights reserved. - see
 * license.txt for details.
 * 
 * Description: DeviceEvent class.
 */
package net.realmproject.dcm.event;


import java.io.Serializable;
import java.util.Date;

import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.messaging.DeviceMessageType;


/**
 * 
 * A DeviceEvent consists of an id representing the device of publication, a
 * type representing the intent of the message, and an optional
 * {@link Serializable} payload. <br>
 * <br>
 * DeviceEvents also have timestamp, and region properties. The region property
 * is an arbitrary string representing what part of a larger network of devices
 * the event originated from, and is usually not set by the publishing device,
 * but by a component like a {@link DeviceEventBus}
 * 
 * @author chabotd, NAS
 *
 */
public class IDeviceEvent implements Serializable, DeviceEvent {

    private static final long serialVersionUID = 1L;

    /**
     * The device which has emitted this event
     */
    private String deviceId;

    /**
     * The type of event/message
     */
    private DeviceMessageType type;

    /**
     * The value or payload of this event
     */
    private Serializable value;

    /**
     * The time (as recorded by the computer of origin) that this event was
     * issued
     */
    private Date timestamp;

    /**
     * The location (event bus) that this event was issued. Larger device
     * networks can be composed of multiple event busses, bridged over a message
     * queuing system. The region field identifies which event bus the event is
     * from, and allows events to be filtered by region. See
     * {@link DeviceEventBus}
     */
    private String region = null;
    private boolean privateEvent = false;

    /**************************************************************************/

    /**
     * Creates a new DeviceEvent with no payload and a current timestamp
     * 
     * @param type
     *            The type of this message
     * @param deviceId
     *            The id of the originating device
     */
    public IDeviceEvent(DeviceMessageType type, String deviceId) {
        this(type, deviceId, null, new Date());
    }

    /**
     * Creates a new DeviceEvent with the given payload and a current timestamp
     * 
     * @param type
     *            The type of this message
     * @param deviceId
     *            The id of the originating device
     * @param value
     *            the payload for this event
     */
    public IDeviceEvent(DeviceMessageType type, String deviceId, Serializable value) {
        this(type, deviceId, value, new Date());
    }

    /**
     * Creates a new DeviceEvent with the given payload and timestamp
     * 
     * @param type
     *            The type of this message
     * @param deviceId
     *            The id of the originating device
     * @param value
     *            The payload for this event
     * @param timestamp
     *            The timestamp this event was issued at
     */
    public IDeviceEvent(DeviceMessageType type, String deviceId, Serializable value, Date timestamp) {
        this.deviceId = deviceId;
        this.type = type;
        this.value = value;
        this.timestamp = timestamp;
    }

    @Override
    public String getDeviceId() {
        return deviceId;
    }

    @Override
    public DeviceMessageType getDeviceMessageType() {
        return type;
    }

    @Override
    public Serializable getValue() {
        return value;
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String getRegion() {
        return region;
    }

    @Override
    public void setRegion(String region) {
        this.region = region;
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

        if (deviceId != null) {
            str += " from " + deviceId;
        }

        return str;
    }
}
