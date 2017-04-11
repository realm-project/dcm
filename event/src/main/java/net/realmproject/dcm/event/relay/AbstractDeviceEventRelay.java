package net.realmproject.dcm.event.relay;


import net.realmproject.dcm.event.IDeviceEventNode;
import net.realmproject.dcm.event.identity.Identity;
import net.realmproject.dcm.util.DCMUtil;


public class AbstractDeviceEventRelay extends IDeviceEventNode implements DeviceEventRelay, Identity {

    private boolean sending = true;
    private String id = DCMUtil.generateId(getClass());

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
