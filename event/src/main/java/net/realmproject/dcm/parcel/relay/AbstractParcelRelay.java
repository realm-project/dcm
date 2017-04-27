package net.realmproject.dcm.parcel.relay;


import net.realmproject.dcm.parcel.IParcelNode;
import net.realmproject.dcm.parcel.identity.Identity;
import net.realmproject.dcm.util.DCMUtil;


public class AbstractParcelRelay extends IParcelNode implements ParcelRelay, Identity {

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
