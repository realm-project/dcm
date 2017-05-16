package net.realmproject.dcm.network.impl.dummy;

import net.realmproject.dcm.network.WireBytesReceiver;
import net.realmproject.dcm.network.impl.IWireSender;

public class DummyWireSender extends IWireSender {

	WireBytesReceiver receiver;
	
	public DummyWireSender(WireBytesReceiver receiver) {
		this.receiver = receiver;
	}

	@Override
	public void wireSend(byte[] serializedParcel) {
		receiver.wireReceive(serializedParcel);
	}

}
