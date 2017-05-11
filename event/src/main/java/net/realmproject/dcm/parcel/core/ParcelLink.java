package net.realmproject.dcm.parcel.core;

/**
 * 
 * Interface for parcel graph nodes which relay parcels to other nodes
 * 
 * @author NAS
 *
 */
public interface ParcelLink extends ParcelNode, ParcelReceiver, ParcelSender {

	@Override
	default void receive(Parcel<?> parcel) {
		send(parcel);
	}

	void setReceiver(ParcelReceiver receiver);

	ParcelReceiver getReceiver();
	

	default ParcelLink link(ParcelLink link) {
		setReceiver(link);
		return link;
	}
	
	default void link(ParcelReceiver receiver) {
		setReceiver(receiver);
	}
	
	
}
