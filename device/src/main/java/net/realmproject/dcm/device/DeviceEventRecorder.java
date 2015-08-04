package net.realmproject.dcm.device;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.receiver.DeviceEventReceiver;
import net.realmproject.dcm.features.recording.RecordWriter;
import net.realmproject.dcm.features.recording.Recorder;


public class DeviceEventRecorder extends Recorder<DeviceEvent> implements DeviceEventReceiver {

    private Predicate<DeviceEvent> filter = a -> true;

    public DeviceEventRecorder(DeviceEventBus bus, RecordWriter<DeviceEvent> writer) {
        this(bus, writer, e -> true);
    }

    public DeviceEventRecorder(DeviceEventBus bus, RecordWriter<DeviceEvent> writer, Predicate<DeviceEvent> filter) {
        super(writer);
        setFilter(filter);
        bus.subscribe(this::test, this::accept);
    }

    public Predicate<DeviceEvent> getFilter() {
        return filter;
    }

    public void setFilter(Predicate<DeviceEvent> filter) {
        this.filter = filter;
    }
    
    private boolean test(DeviceEvent event) {
    	return filter.test(event);
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
