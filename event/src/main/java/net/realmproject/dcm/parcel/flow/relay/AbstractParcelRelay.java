package net.realmproject.dcm.parcel.flow.relay;


import net.realmproject.dcm.parcel.node.IParcelNode;
import net.realmproject.dcm.parcel.node.identity.Identity;
import net.realmproject.dcm.parcel.node.receiver.ParcelReceiver;


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
