package net.realmproject.dcm.features.statefulness;


import java.util.Map;
import java.util.UUID;

import net.realmproject.dcm.features.Identity;
import net.realmproject.dcm.features.Properties;
import net.realmproject.dcm.features.recording.Recordable;


public class State implements Recordable, Identity, Properties<Object> {

    private Map<String, Object> properties;
    private String id;
    private boolean toRecord;

    public State() {
        id = UUID.randomUUID().toString();
    }

    public enum Mode {
        IDLE, BUSY, ERROR, DISCONNECTED, UNKNOWN;
    }

    public Mode mode = Mode.IDLE;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean isToRecord() {
        // TODO Auto-generated method stub
        return toRecord;
    }

    @Override
    public void setToRecord(boolean toRecord) {
        this.toRecord = toRecord;
    }

    @Override
    public void setPropertyMap(Map<String, Object> propertyMap) {
        this.properties = propertyMap;
    }

    @Override
    public Map<String, Object> getPropertyMap() {
        return properties;
    }

    public void setMessageType(String messageType) {}

    public String getMessageType() {
        return null;
    }
}