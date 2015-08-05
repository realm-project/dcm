package net.realmproject.dcm.stock.event;

import java.io.Serializable;

import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.network.WireMessage;
import net.realmproject.dcm.network.transcoder.Transcoder;

public class WrappingTranscoder implements Transcoder<WireMessage, Serializable> {

	@Override
	public Serializable encode(WireMessage message) {
		return message.getEvent().getPayload();
	}

	@Override
	public WireMessage decode(Serializable message) {
		return new WireMessage(new IDeviceEvent(DeviceEventType.MESSAGE, "sourceId", null, message));
	}

}
