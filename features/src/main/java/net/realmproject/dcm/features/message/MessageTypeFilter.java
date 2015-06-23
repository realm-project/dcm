package net.realmproject.dcm.features.message;


import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventType;


public class MessageTypeFilter implements Predicate<DeviceEvent> {

    private String messageType;

    public MessageTypeFilter(String messageType) {
        this.messageType = messageType;
    }

    @Override
    public boolean test(DeviceEvent t) {
        if (t.getDeviceEventType() != DeviceEventType.MESSAGE) { return false; }
        Message message = (Message) t.getValue();
        return messageType.equals(message.getMessageType());
    }

}
