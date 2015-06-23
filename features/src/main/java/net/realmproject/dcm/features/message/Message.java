package net.realmproject.dcm.features.message;


import java.util.UUID;

import net.realmproject.dcm.features.Identity;
import net.realmproject.dcm.features.recording.Recordable;


public class Message implements Recordable, Identity {

    private String messageType;
    private String id;
    private boolean toRecord;

    public Message(String messageType) {
        setMessageType(messageType);
        setId(UUID.randomUUID().toString());
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean isToRecord() {
        return toRecord;
    }

    @Override
    public void setToRecord(boolean toRecord) {
        this.toRecord = toRecord;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

}
