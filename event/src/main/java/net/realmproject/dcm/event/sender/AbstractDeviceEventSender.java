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
 * 
 * @author maxweld, NAS
 *
 */

public abstract class AbstractDeviceEventSender implements DeviceEventSender, DeviceEventSenderControl {

    private boolean sending = false;
    private long sentEvents = 0L;

    @Override
    public boolean send(DeviceEvent event) {
        if (event == null) { return false; }
        if (!isSending()) { return false; }

        boolean result = doSend(event.deepCopy());
        if (result) {
            incrementSentEvents();
        }

        return result;
    }

    // this method is intended to allow different implementations of the sending
    // action but any attempt to actually send an event should go send(event),
    // since it wraps this method and performs required bookkeeping that this
    // method does not
    /**
     * This method is for internal use only, and should not be called. Call
     * send(event) instead.
     * 
     * @param event
     *            the event to send
     * @return true if sending is successful, false otherwise
     */
    protected abstract boolean doSend(DeviceEvent event);

    private void incrementSentEvents() {
        sentEvents++;
    }

    @Override
    public boolean isSending() {
        return sending;
    }

    @Override
    public void setSending(boolean sending) {
        if (this.sending != sending) {
            this.sending = sending;
            if (sending) {
                sentEvents = 0L;
            }
        }
    }

    @Override
    public void startSending() {
        setSending(true);
    }

    @Override
    public void stopSending() {
        setSending(false);
    }

    @Override
    public boolean hasSent() {
        return (sentEvents > 0L);
    }

    @Override
    public long sendCount() {
        return sentEvents;
    }

}
