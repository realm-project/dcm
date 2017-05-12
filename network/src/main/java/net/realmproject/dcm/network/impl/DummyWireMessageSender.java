package net.realmproject.dcm.network.impl;

import net.realmproject.dcm.network.WireReceiver;

public class DummyWireMessageSender extends IWireSender {

	WireReceiver receiver;
	
	public DummyWireMessageSender(WireReceiver receiver) {
		this.receiver = receiver;
	}

	@Override
	public void send(byte[] serializedParcel) {
		receiver.receive(serializedParcel);
	}

}
