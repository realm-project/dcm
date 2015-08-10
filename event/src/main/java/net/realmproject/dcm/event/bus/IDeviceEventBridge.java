package net.realmproject.dcm.event.bus;


import net.realmproject.dcm.event.IDeviceEventNode;
import net.realmproject.dcm.event.relay.DeviceEventRelay;


/**
 * A Bridge is a two-way {@link DeviceEventRelay} between two
 * {@link DeviceEventBus}ses
 * 
 * @author NAS
 *
 */

public class IDeviceEventBridge extends IDeviceEventNode {

    public IDeviceEventBridge(DeviceEventBus bus1, DeviceEventBus bus2) {

        bus1.subscribe(this::filter, event -> bus2.accept(transform(event)));
        bus2.subscribe(this::filter, event -> bus1.accept(transform(event)));

    }

}
