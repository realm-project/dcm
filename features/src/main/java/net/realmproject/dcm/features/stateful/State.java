package net.realmproject.dcm.features.stateful;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.realmproject.dcm.event.identity.Identity;
import net.realmproject.dcm.features.Properties;
import net.realmproject.dcm.features.recording.Recordable;
import net.realmproject.dcm.util.DCMUtil;


public class State implements Recordable, Identity, Properties<Serializable>, Serializable {

    private Map<String, Serializable> properties = new HashMap<>();
    private String id;
    private boolean toRecord;

    public State() {
        id = DCMUtil.generateId();
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
    public void setProperties(Map<String, Serializable> propertyMap) {
        this.properties = propertyMap;
    }

    @Override
    public Map<String, Serializable> getProperties() {
        return properties;
    }

    public void setMessageType(String messageType) {}

    public String getMessageType() {
        return null;
    }
}