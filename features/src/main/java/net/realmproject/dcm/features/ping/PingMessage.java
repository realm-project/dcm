package net.realmproject.dcm.features.ping;


import java.util.Map;

import net.realmproject.dcm.features.Properties;
import net.realmproject.dcm.features.message.Message;


public class PingMessage extends Message implements Properties<Object> {

    public static final String PING_MESSAGE_TYPE = "PingMessage";

    Ping ping;
    private Map<String, Object> properties;

    public PingMessage(Ping ping) {
        super(PING_MESSAGE_TYPE);
        this.ping = ping;
    }

    public Ping getPing() {
        return ping;
    }

    public void setPing(Ping ping) {
        this.ping = ping;
    }

    @Override
    public void setPropertyMap(Map<String, Object> propertyMap) {
        this.properties = propertyMap;
    }

    @Override
    public Map<String, Object> getPropertyMap() {
        return this.properties;
    }

}
