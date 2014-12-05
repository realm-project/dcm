package net.realmproject.dcm.accessor.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.realmproject.dcm.messaging.DeviceMessage;
import net.realmproject.dcm.messaging.DeviceMessageSender;
import net.realmproject.dcm.messaging.DeviceMessageType;

public class DeviceWriterUtil
{
	public static boolean send(String deviceId, DeviceMessageSender sender, DeviceMessageType type) {
		return send(deviceId, sender, type, new HashMap<String,Serializable>());
	}
	
	public static boolean send(String deviceId, DeviceMessageSender sender, DeviceMessageType type, Map<? extends String, ? extends Serializable> values) {
		if(sender == null) return false;
		
		DeviceMessage<HashMap<String,Serializable>> deviceMessage =	new DeviceMessage<>();
		
		deviceMessage.setDeviceId(deviceId);
		deviceMessage.setTimestamp(new Date());
		deviceMessage.setValue(new HashMap<>(values));
		deviceMessage.setDeviceMessageType(type);
		
		sender.send(deviceMessage);
		return true;
	}
}
