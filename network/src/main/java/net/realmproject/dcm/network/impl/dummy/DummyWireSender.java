package net.realmproject.dcm.network.impl.dummy;

import net.realmproject.dcm.network.WireReceiver;
import net.realmproject.dcm.network.impl.IWireSender;

public class DummyWireSender extends IWireSender {

	WireReceiver receiver;
	
	public DummyWireSender(WireReceiver receiver) {
		this.receiver = receiver;
	}

	@Override
	public void wireSend(byte[] serializedParcel) {
		receiver.wireReceive(serializedParcel);
	}

}
