package net.realmproject.dcm.device;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.DeviceEventFilterer;
import net.realmproject.dcm.event.receiver.DeviceEventReceiver;
import net.realmproject.dcm.features.recording.RecordWriter;
import net.realmproject.dcm.features.recording.Recorder;


public class DeviceEventRecorder extends Recorder<DeviceEvent>implements DeviceEventReceiver, DeviceEventFilterer {

    private List<Predicate<DeviceEvent>> filters = new ArrayList<>();

    public DeviceEventRecorder(DeviceEventBus bus, RecordWriter<DeviceEvent> writer) {
        this(bus, writer, e -> true);
    }

    public DeviceEventRecorder(DeviceEventBus bus, RecordWriter<DeviceEvent> writer, Predicate<DeviceEvent> filter) {
        super(writer);
        setFilters(filter);
        bus.subscribe(this::filter, this::accept);
    }

    public DeviceEventRecorder(DeviceEventBus bus, RecordWriter<DeviceEvent> writer,
            List<Predicate<DeviceEvent>> filters) {
        super(writer);
        setFilters(filters);
        bus.subscribe(this::filter, this::accept);
    }

    @Override
    public List<Predicate<DeviceEvent>> getFilters() {
        return filters;
    }

    @Override
    public void setFilters(List<Predicate<DeviceEvent>> filters) {
        this.filters.clear();
        this.filters.addAll(filters);
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

}
