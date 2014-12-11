package net.realmproject.dcm.event.sender;


import net.realmproject.dcm.event.IDeviceEvent;


/**
 * 
 * @author maxweld, NAS
 *
 */

public abstract class AbstractDeviceEventSender implements DeviceEventSender {

    private boolean sending = false;
    private long sentEvents = 0L;

    /**
     * Sends the given {@link IDeviceEvent}. If this component is not currently
     * sending events (eg stopSending() has been called) this event will be
     * discarded.
     * 
     * @param event
     *            the event to send
     * @return true if sending is successful, false otherwise
     */
    protected boolean send(IDeviceEvent event) {
        if (event == null) { return false; }
        if (!isSending()) { return false; }

        boolean result = doSend(event);
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
    protected abstract boolean doSend(IDeviceEvent event);

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
