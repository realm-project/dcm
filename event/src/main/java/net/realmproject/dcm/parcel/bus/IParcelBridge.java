package net.realmproject.dcm.parcel.bus;


import net.realmproject.dcm.parcel.IParcelNode;
import net.realmproject.dcm.parcel.relay.ParcelRelay;


/**
 * A Bridge is a two-way {@link ParcelRelay} between two
 * {@link ParcelHub}ses
 * 
 * @author NAS
 *
 */

public class IParcelBridge extends IParcelNode {

    public IParcelBridge(ParcelHub bus1, ParcelHub bus2) {

        bus1.subscribe(this::filter, parcel -> bus2.accept(transform(parcel)));
        bus2.subscribe(this::filter, parcel -> bus1.accept(transform(parcel)));

    }

}
