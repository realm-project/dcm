package net.realmproject.dcm.event.relay;


import java.util.function.Function;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;


public class IDeviceEventRelay implements DeviceEventRelay {

    private Predicate<DeviceEvent> filter = null;
    private Function<DeviceEvent, DeviceEvent> transform = null;
    private boolean sending = true;

    @Override
    public Predicate<DeviceEvent> getFilter() {
        return filter;
    }

    @Override
    public void setFilter(Predicate<DeviceEvent> filter) {
        this.filter = filter;
    }

    @Override
    public Function<DeviceEvent, DeviceEvent> getTransform() {
        return transform;
    }

    @Override
    public void setTransform(Function<DeviceEvent, DeviceEvent> transform) {
        this.transform = transform;
    }

    @Override
    public boolean isSending() {
        return sending;
    }

    @Override
    public void setSending(boolean sending) {
        this.sending = sending;
    }

}
