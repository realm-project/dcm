package net.realmproject.dcm.event;


import java.util.function.Function;
import java.util.function.Predicate;


public class IDeviceEventNode implements DeviceEventNode {

    private Predicate<DeviceEvent> filter = null;
    private Function<DeviceEvent, DeviceEvent> transform = null;

    public IDeviceEventNode() {
        super();
    }

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

}