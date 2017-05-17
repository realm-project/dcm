package net.realmproject.dcm.parcel.core.link;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelNode;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;

/**
 * 
 * Interface for parcel graph nodes which relay parcels to other nodes
 * 
 * @author NAS
 *
 */
public interface ParcelLink extends ParcelNode, ParcelReceiver, ParcelSender, Linkable {

	// Override this so that implementnig classes only have to implement the
	// send mehtod, rather than both
	@Override
	default void receive(Parcel<?> parcel) {
		send(parcel);
	}

}
