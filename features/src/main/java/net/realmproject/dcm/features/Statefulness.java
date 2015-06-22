package net.realmproject.dcm.features;


import java.io.Serializable;


public interface Statefulness<T extends Statefulness.State> extends Publishing {

    /**
     * Base class for {@link CommandDevice}s to report their device state
     * 
     * @author NAS
     *
     */
    public class State implements Serializable, Identity {

        private String id;

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
