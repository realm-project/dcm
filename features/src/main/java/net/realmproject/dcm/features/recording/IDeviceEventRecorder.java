package net.realmproject.dcm.features.recording;


import java.util.function.Predicate;

import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.node.ParcelNode;
import net.realmproject.dcm.parcel.node.hub.ParcelHub;
import net.realmproject.dcm.parcel.node.receiver.ParcelReceiver;
import net.realmproject.dcm.util.DCMUtil;



public class IDeviceEventRecorder extends IRecorder<Parcel<?>> implements ParcelReceiver, ParcelNode {

	private String id = DCMUtil.generateId();

    public IDeviceEventRecorder(ParcelHub bus, RecordWriter<Parcel<?>> writer) {
        this(bus, writer, e -> true);
    }

    public IDeviceEventRecorder(ParcelHub bus, RecordWriter<Parcel<?>> writer, Predicate<Parcel<?>> filter) {
        super(writer);
        bus.subscribe(this);
    }

    @Override
    public void receive(Parcel<?> event) {
        try {
            record(event);
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
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
