package net.realmproject.dcm.features;


import java.io.Serializable;

import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.identity.Identity;
import net.realmproject.dcm.event.sender.DeviceEventSender;


public interface Publishing extends Identity, DeviceEventSender {

    default void publish(Object o) {
        publish(o, DeviceEventType.VALUE_CHANGED);
    }

    default void publish(Object o, DeviceEventType type) {
        publish(o, null, type);
    }

    default void publish(Object o, String targetId, DeviceEventType type) {
        send(new IDeviceEvent(type, getId(), targetId, (Serializable) o));
    }

}
