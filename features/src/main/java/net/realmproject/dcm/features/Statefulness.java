package net.realmproject.dcm.features;

import net.realmproject.dcm.util.DCMSerialize;

public interface Statefulness<T extends Statefulness.State> extends Publishing {

	/**
	 * Base class for {@link CommandDevice}s to report their device state
	 * 
	 * @author NAS
	 *
	 */
	public class State {

	    public enum Mode {
	        IDLE, BUSY, ERROR, DISCONNECTED, UNKNOWN;
	    }

	    public Mode mode = Mode.IDLE;
	}

	
    /**
     * Queries the state of this Device. This method should return an
     * object representing the full state of this device. While representing
     * this device's state, this value should be stateless in the sense that an
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
    	publish(DCMSerialize.structToMap(getState()));
    }
    
	
}