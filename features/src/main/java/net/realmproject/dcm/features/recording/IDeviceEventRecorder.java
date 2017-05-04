package net.realmproject.dcm.features.recording;


import java.util.function.Function;
import java.util.function.Predicate;

import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.ParcelNode;
import net.realmproject.dcm.parcel.bus.ParcelHub;
import net.realmproject.dcm.parcel.receiver.ParcelReceiver;
import net.realmproject.dcm.util.DCMUtil;



public class IDeviceEventRecorder extends IRecorder<Parcel<?>> implements ParcelReceiver, ParcelNode {

	private String id = DCMUtil.generateId();
    private Predicate<Parcel<?>> filter = null;
    private Function<Parcel<?>, Parcel<?>> transform = null;

    public IDeviceEventRecorder(ParcelHub bus, RecordWriter<Parcel<?>> writer) {
        this(bus, writer, e -> true);
    }

    public IDeviceEventRecorder(ParcelHub bus, RecordWriter<Parcel<?>> writer, Predicate<Parcel<?>> filter) {
        super(writer);
        setFilter(filter);
        bus.subscribe(this::filter, this);
    }

    @Override
    public void receive(Parcel<?> event) {
        try {
            record(transform(event));
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public Predicate<Parcel<?>> getFilter() {
        return filter;
    }

    public void setFilter(Predicate<Parcel<?>> filter) {
        this.filter = filter;
    }

    @Override
    public Function<Parcel<?>, Parcel<?>> getTransform() {
        return transform;
    }

    @Override
    public void setTransform(Function<Parcel<?>, Parcel<?>> transform) {
        this.transform = transform;
    }

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

}
