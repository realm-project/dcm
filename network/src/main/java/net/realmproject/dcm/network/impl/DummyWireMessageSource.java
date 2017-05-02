package net.realmproject.dcm.network.impl;

import net.realmproject.dcm.network.WireMessage;
import net.realmproject.dcm.network.WireMessageSink;
import net.realmproject.dcm.parcel.bus.ParcelHub;

public class DummyWireMessageSource extends IWireMessageSource {

	WireMessageSink sink;
	
	public DummyWireMessageSource(ParcelHub bus, WireMessageSink sink) {
		super(bus);
		this.sink = sink;
	}

	@Override
	public boolean send(WireMessage deviceMessage) {
		sink.receive(deviceMessage);
		return true;
	}

}
