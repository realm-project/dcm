/**
 * Copyright 2014 Realm Project
 * 
 * This file is part of the Device Control Module (DCM).
 * 
 * DCM is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DCM is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DCM. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package net.realmproject.dcm.event.sender;


import net.realmproject.dcm.event.DeviceEvent;


/**
 * A DeviceEventSender is anything which sends events. It is not required that
 * it generate events of it's own.
 * 
 * @author maxweld, NAS
 *
 */
public interface DeviceEventSender {

    /**
     * Sends the given {@link DeviceEvent}. If this component is not currently
     * sending events (eg stopSending() has been called) this event will be
     * discarded.
     * 
     * @param event
     *            the event to send
     * @return true if sending is successful, false otherwise
     */
    boolean send(DeviceEvent event);

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
        setSending(false);
    }

    /**
     * Returns true if this component has sent events since sending of events
     * was enabled. Disabling sending of events will reset this counter to 0
     * 
     * @return true if events have been sent, false otherwise
     */
    default boolean hasSent() {
        return (sendCount() > 0L);
    }

    /**
     * The number of events events which have been sent by this component since
     * it started sending events. Disabling sending of events will reset this
     * counter to 0
     * 
     * @return number of sent messages
     */
    long sendCount();

}
