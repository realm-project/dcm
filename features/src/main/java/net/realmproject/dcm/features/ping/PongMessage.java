package net.realmproject.dcm.features.ping;


import java.util.Map;

import net.realmproject.dcm.features.Properties;
import net.realmproject.dcm.features.message.Message;


public class PongMessage extends Message implements Properties<Object> {

    public static final String PONG_MESSAGE_TYPE = "PongMessage";

    private Map<String, Object> properties;

    private Ping ping;

    public PongMessage(PingMessage pingMessage) {
        super(PONG_MESSAGE_TYPE);
        setPropertyMap(pingMessage.getPropertyMap());
        ping = pingMessage.getPing();
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
