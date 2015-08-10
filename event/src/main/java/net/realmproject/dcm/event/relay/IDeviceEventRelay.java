package net.realmproject.dcm.event.relay;

import net.realmproject.dcm.event.IDeviceEventNode;

public class IDeviceEventRelay extends IDeviceEventNode implements DeviceEventRelay {

    private boolean sending = true;

    @Override
    public boolean isSending() {
        return sending;
    }

    @Override
    public void setSending(boolean sending) {
        this.sending = sending;
    }

}
