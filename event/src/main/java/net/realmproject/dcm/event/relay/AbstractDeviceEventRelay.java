package net.realmproject.dcm.event.relay;


import java.util.UUID;

import net.realmproject.dcm.event.IDeviceEventNode;
import net.realmproject.dcm.event.identity.Identity;


public class AbstractDeviceEventRelay extends IDeviceEventNode implements DeviceEventRelay, Identity {

    private boolean sending = true;
    private String id = getClass().getSimpleName() + ":" + UUID.randomUUID().toString();

    @Override
    public boolean isSending() {
        return sending;
    }

    @Override
    public void setSending(boolean sending) {
        this.sending = sending;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

}
