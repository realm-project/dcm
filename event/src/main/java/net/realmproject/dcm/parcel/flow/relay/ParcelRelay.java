package net.realmproject.dcm.parcel.flow.relay;


import net.realmproject.dcm.parcel.node.ParcelNode;
import net.realmproject.dcm.parcel.node.publisher.ParcelPublisher;
import net.realmproject.dcm.parcel.Parcel;


/**
 * 
 * Interface for parcel graph nodes which relay parcels to other nodes, either
 * by being {@link ParcelPublisher}s
 * 
 * @author NAS
 *
 */
public interface ParcelRelay extends ParcelNode {

    /** Method for filtering {@link Parcel}s using the specified filter **/
    default boolean filter(Parcel<?> parcel) {
        if (!isSending()) { return false; }
        if (getFilter() == null) { return true; }
        if (!getFilter().test(parcel)) { return false; }
        return true;
    }

    // Node Sending/Relaying Enabled/Disabled
    /**
     * Is sending of parcels turned on?
     * 
     * @return true if this component is currently sending parcels, false
     *         otherwise
     */
    boolean isSending();

    /**
     * Set if this component is currently sending parcels. There is no backlog of
     * unsent parcels retained. Parcels "sent" while this component was not
     * sending are lost.
     * 
     * @param sending
     *            whether to send parcels or not
     */
    void setSending(boolean sending);

    /**
     * If this component is not currently sending parcels, start sending them
     * now. There is no backlog of unsent parcels retained. Parcels "sent" while
     * this component was not sending are lost.
     */
    default void startSending() {
        setSending(true);
    }

    /**
     * If this component is currently sending parcels, stop sending them now.
     * There is no backlog of unsent parcels retained. Parcels "sent" while this
     * component was not sending are lost.
     */
    default void stopSending() {
        setSending(true);
    }

}
