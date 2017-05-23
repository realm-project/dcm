package net.realmproject.dcm.parcel.core;

import net.realmproject.dcm.parcel.impl.parcel.IParcel;

/**
 * Base interface for parcel nodes which receive {@link Parcel}s
 * 
 * @author NAS
 *
 */

public interface ParcelReceiver extends Identity {

    void receive(Parcel<?> parcel);
    
    default void receive(Object payload) {
    	receive(new IParcel<>().payload(payload));
    }

}
