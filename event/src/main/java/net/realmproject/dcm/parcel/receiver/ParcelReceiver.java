package net.realmproject.dcm.parcel.receiver;


import net.realmproject.dcm.parcel.Parcel;


/**
 * Base interface for parcel nodes which receive {@link Parcel}s
 * 
 * @author NAS
 *
 */
public interface ParcelReceiver {

    void accept(Parcel parcel);

}
