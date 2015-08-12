package net.realmproject.dcm.features.recording;


import java.util.function.Function;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventNode;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.receiver.DeviceEventReceiver;


public class IDeviceEventRecorder extends IRecorder<DeviceEvent>implements DeviceEventReceiver, DeviceEventNode {

    private Predicate<DeviceEvent> filter = null;
    private Function<DeviceEvent, DeviceEvent> transform = null;

    public IDeviceEventRecorder(DeviceEventBus bus, RecordWriter<DeviceEvent> writer) {
        this(bus, writer, e -> true);
    }

    public IDeviceEventRecorder(DeviceEventBus bus, RecordWriter<DeviceEvent> writer, Predicate<DeviceEvent> filter) {
        super(writer);
        setFilter(filter);
        bus.subscribe(this::filter, this::accept);
    }

    @Override
    public void accept(DeviceEvent event) {
        try {
            record(transform(event));
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public Predicate<DeviceEvent> getFilter() {
        return filter;
    }

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
