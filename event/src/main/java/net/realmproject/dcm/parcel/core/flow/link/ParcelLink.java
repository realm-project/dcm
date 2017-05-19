package net.realmproject.dcm.parcel.core.flow.link;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelNode;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;
import net.realmproject.dcm.parcel.core.linkable.SingleLinkable;

/**
 * 
 * Interface for parcel graph nodes which relay parcels to one other node
 * 
 * @author NAS
 *
 */
public interface ParcelLink extends ParcelNode, ParcelReceiver, ParcelSender, SingleLinkable {

	// Override this so that implementnig classes only have to implement the
	// send mehtod, rather than both
	@Override
	default void receive(Parcel<?> parcel) {
		send(parcel);
	}

}
