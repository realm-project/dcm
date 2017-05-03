package net.realmproject.dcm.parcel.receiver;


import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.identity.Identity;


/**
 * Base interface for parcel nodes which receive {@link Parcel}s
 * 
 * @author NAS
 *
 */

public interface ParcelReceiver extends Identity {

    void accept(Parcel<?> parcel);

}
