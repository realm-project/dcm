package net.realmproject.dcm.parcel.relay;


import net.realmproject.dcm.parcel.IParcelNode;
import net.realmproject.dcm.parcel.identity.Identity;
import net.realmproject.dcm.parcel.receiver.ParcelReceiver;


public abstract class AbstractParcelRelay extends IParcelNode implements ParcelRelay, ParcelReceiver, Identity {

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
