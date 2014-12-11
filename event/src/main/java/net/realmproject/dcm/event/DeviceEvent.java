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