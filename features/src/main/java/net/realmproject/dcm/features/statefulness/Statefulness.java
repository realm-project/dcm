package net.realmproject.dcm.features.statefulness;


import net.realmproject.dcm.features.Publishing;


public interface Statefulness<T extends State> extends Publishing {

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
        publishState(getState());
    }

    /**
     * Publishes the given state as a VALUE_CHANGED event
     */
    default void publishState(State state) {
        publish(state);
    }

}
