package net.realmproject.dcm.features.statefulness;


import java.util.Map;
import java.util.UUID;

import net.realmproject.dcm.event.identity.Identity;
import net.realmproject.dcm.features.Properties;
import net.realmproject.dcm.features.Publishing;
import net.realmproject.dcm.features.recording.Recordable;


public interface Statefulness<T extends Statefulness.State> extends Publishing {

    /**
     * Base class for {@link CommandDevice}s to report their device state
     * 
     * @author NAS
     *
     */
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

    /**
     * Queries the state of this Device. This method should return an object
     * representing the full state of this device. While representing this
     * device's state, this value should be stateless in the sense that an
     * observer need not rely on previous values in order to properly understand
     * this one.
     * 
     * @return the current state of this device
     */
    T getState();

    /**
     * Publishes the result of the getState method as a VALUE_CHANGED event
     */
    default void publishState() {
        publish(getState());
    }

    /**
     * Publishes the given state as a VALUE_CHANGED event
     */
    default void publishState(State state) {
        publish(state);
    }

}
