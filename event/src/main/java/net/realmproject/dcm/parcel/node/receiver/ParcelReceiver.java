package net.realmproject.dcm.parcel.node.receiver;


import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.node.identity.Identity;


/**
 * Base interface for parcel nodes which receive {@link Parcel}s
 * 
 * @author NAS
 *
 */

public interface ParcelReceiver extends Identity {

    void receive(Parcel<?> parcel);

}
