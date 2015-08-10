package net.realmproject.dcm.device;


import java.util.function.Function;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.DeviceEventNode;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.receiver.DeviceEventReceiver;
import net.realmproject.dcm.features.recording.RecordWriter;
import net.realmproject.dcm.features.recording.Recorder;


public class DeviceEventRecorder extends Recorder<DeviceEvent>implements DeviceEventReceiver, DeviceEventNode {

    private Predicate<DeviceEvent> filter = a -> true;
    private Function<DeviceEvent, DeviceEvent> transform = a -> a;

    public DeviceEventRecorder(DeviceEventBus bus, RecordWriter<DeviceEvent> writer) {
        this(bus, writer, e -> true);
    }

    public DeviceEventRecorder(DeviceEventBus bus, RecordWriter<DeviceEvent> writer, Predicate<DeviceEvent> filter) {
        super(writer);
        setFilter(filter);
        bus.subscribe(this::filter, this::accept);
    }

    @Override
    public boolean accept(DeviceEvent event) {
        try {
            record(event);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
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
