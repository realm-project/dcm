package net.realmproject.dcm.device;


import java.util.List;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.Filters;
import net.realmproject.dcm.features.recording.RecordWriter;
import net.realmproject.dcm.features.recording.Recorder;


public class DeviceEventRecorder extends Recorder<DeviceEvent> {

    public DeviceEventRecorder(DeviceEventBus bus, RecordWriter<DeviceEvent> writer) {
        this(bus, writer, e -> true);
    }

    public DeviceEventRecorder(DeviceEventBus bus, RecordWriter<DeviceEvent> writer,
            List<Predicate<DeviceEvent>> filters) {
        this(bus, writer, Filters.all(filters));
    }

    public DeviceEventRecorder(DeviceEventBus bus, RecordWriter<DeviceEvent> writer, Predicate<DeviceEvent> filter) {
        super(writer);
        bus.subscribe(filter, (event) -> {
            try {
                record(event);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
