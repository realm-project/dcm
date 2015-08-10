package net.realmproject.dcm.event.relay;


import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventNode;
import net.realmproject.dcm.event.source.DeviceEventSource;


/**
 * 
 * Interface for DCM event graph nodes which relay events to other nodes, either
 * by being {@link DeviceEventSource}s
 * 
 * @author NAS
 *
 */
public interface DeviceEventRelay extends DeviceEventNode {

    /** Method for filtering {@link DeviceEvent}s using the specified filter **/
    default boolean filter(DeviceEvent event) {
        if (!isSending()) { return false; }
        if (!getFilter().test(event)) { return false; }
        return true;
    }

    // Node Sending/Relaying Enabled/Disabled
    /**
     * Is sending of events turned on?
     * 
     * @return true if this component is currently sending events, false
     *         otherwise
     */
    boolean isSending();

    /**
     * Set if this component is currently sending events. There is no backlog of
     * unsent events retained. Events "sent" while this component was not
     * sending are lost.
     * 
     * @param sending
     *            whether to send events or not
     */
    void setSending(boolean sending);

    /**
     * If this component is not currently sending events, start sending them
     * now. There is no backlog of unsent events retained. Events "sent" while
     * this component was not sending are lost.
     */
    default void startSending() {
        setSending(true);
    }

    /**
     * If this component is currently sending events, stop sending them now.
     * There is no backlog of unsent events retained. Events "sent" while this
     * component was not sending are lost.
     */
    default void stopSending() {
        setSending(true);
    }

}
