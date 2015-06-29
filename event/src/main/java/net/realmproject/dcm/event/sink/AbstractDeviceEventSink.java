package net.realmproject.dcm.event.sink;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;


public abstract class AbstractDeviceEventSink implements DeviceEventSink {

    private List<Predicate<DeviceEvent>> filters = new ArrayList<>();

    @Override
    public List<Predicate<DeviceEvent>> getFilters() {
        return filters;
    }

    @Override
    public void setFilters(List<Predicate<DeviceEvent>> filters) {
        this.filters = filters;
    }

    @Override
    public void receive(DeviceEvent event) {
        if (filter(event)) {
            doReceive(event);
        }
    }

    public abstract void doReceive(DeviceEvent event);

}
