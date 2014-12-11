package net.realmproject.dcm.event.bus;


import java.util.function.Predicate;

import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.filter.AcceptFilter;
import net.realmproject.dcm.event.sender.AbstractDeviceEventSender;


/**
 * Forwards events from one DeviceEventBus to another, applying the optional
 * filter to control which messages are allowed through
 * 
 * @author NAS
 *
 */
public class IDeviceEventBusForwarder extends AbstractDeviceEventSender {

    private DeviceEventBus to;

    public IDeviceEventBusForwarder(DeviceEventBus from, DeviceEventBus to) {
        this(from, to, new AcceptFilter());
        startSending();
    }

    public IDeviceEventBusForwarder(DeviceEventBus from, DeviceEventBus to, Predicate<IDeviceEvent> filter) {
        this.to = to;
        from.subscribe(this::send, filter);
    }

    @Override
    protected boolean doSend(IDeviceEvent event) {
        return to.broadcast(event);
    }
}
