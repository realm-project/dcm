package net.realmproject.dcm.device;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.sink.DeviceEventSink;
import net.realmproject.dcm.features.recording.RecordWriter;
import net.realmproject.dcm.features.recording.Recorder;


public class DeviceEventRecorder extends Recorder<DeviceEvent>implements DeviceEventSink {

    private List<Predicate<DeviceEvent>> filters = new ArrayList<>();

    public DeviceEventRecorder(DeviceEventBus bus, RecordWriter<DeviceEvent> writer) {
        this(bus, writer, e -> true);
    }

    public DeviceEventRecorder(DeviceEventBus bus, RecordWriter<DeviceEvent> writer, Predicate<DeviceEvent> filter) {
        this(bus, writer, Collections.singletonList(filter));
    }

    public DeviceEventRecorder(DeviceEventBus bus, RecordWriter<DeviceEvent> writer,
            List<Predicate<DeviceEvent>> filters) {
        super(writer);

        // set filters
        getFilters().clear();
        getFilters().addAll(filters);

        bus.subscribe(this::filter, this::receive);
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
    public void receive(DeviceEvent event) {
        try {
            record(event);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
