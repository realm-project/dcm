package net.realmproject.dcm.parcel.core;

/**
 * Base interface for parcel nodes which receive {@link Parcel}s
 * 
 * @author NAS
 *
 */

public interface ParcelReceiver extends Identity {

    void receive(Parcel<?> parcel);

}
