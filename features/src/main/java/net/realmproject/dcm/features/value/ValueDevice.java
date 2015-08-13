package net.realmproject.dcm.features.value;


import java.io.Serializable;

import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.identity.Identity;
import net.realmproject.dcm.event.publisher.DeviceEventPublisher;


public interface ValueDevice<T extends Serializable> extends Identity, DeviceEventPublisher {

    /**
     * Gets the current value of this device
     * 
     * @return the value of this device
     */
    T getValue();

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
    void setValue(T val);

    /**
     * Publishes the result of the getValue method as a VALUE_CHANGED event
     */
    default void publishValue() {
        publish(DeviceEventType.VALUE_CHANGED, getId(), null, getValue());
    }

}
