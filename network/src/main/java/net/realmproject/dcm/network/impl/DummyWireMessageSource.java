package net.realmproject.dcm.network.impl;

import net.realmproject.dcm.network.WireSink;
import net.realmproject.dcm.parcel.core.hub.ParcelHub;

public class DummyWireMessageSource extends IWireSource {

	WireSink sink;
	
	public DummyWireMessageSource(ParcelHub bus, WireSink sink) {
		super(bus);
		this.sink = sink;
	}

	@Override
	public boolean send(byte[] serializedParcel) {
		sink.receive(serializedParcel);
		return true;
	}

}
