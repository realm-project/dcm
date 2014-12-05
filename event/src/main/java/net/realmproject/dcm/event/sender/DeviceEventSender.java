/**
 * Copyright (c) Canadian Light Source, Inc. All rights reserved. - see
 * license.txt for details.
 * 
 * Description: DeviceEventPublisher interface.
 */
package net.realmproject.dcm.event.sender;


/**
 * A DeviceEventSender is anything which sends events. It is not required that
 * it generate events of it's own.
 * 
 * @author maxweld, NAS
 *
 */
public interface DeviceEventSender {

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
    void startSending();

    /**
     * If this component is currently sending events, stop sending them now.
     * There is no backlog of unsent events retained. Events "sent" while this
     * component was not sending are lost.
     */
    void stopSending();

    /**
     * Returns true if this component has sent events since sending of events
     * was enabled. Disabling sending of events will reset this counter to 0
     * 
     * @return true if events have been sent, false otherwise
     */
    boolean hasSent();

    /**
     * The number of events events which have been sent by this component since
     * it started sending events. Disabling sending of events will reset this
     * counter to 0
     * 
     * @return number of sent messages
     */
    long sendCount();

}
