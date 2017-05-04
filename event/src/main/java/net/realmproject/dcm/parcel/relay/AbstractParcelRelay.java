package net.realmproject.dcm.parcel.relay;


import net.realmproject.dcm.parcel.IParcelNode;
import net.realmproject.dcm.parcel.identity.Identity;


public class AbstractParcelRelay extends IParcelNode implements ParcelRelay, Identity {

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
