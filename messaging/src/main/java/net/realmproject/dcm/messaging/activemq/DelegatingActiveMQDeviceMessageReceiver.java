/** Copyright (c) Canadian Light Source, Inc. All rights reserved.
 *   - see license.txt for details.
 *
 *  Description:
 * 		DelegatingActiveMQDeviceMessageReceiver class.
 * 
 */
package net.realmproject.dcm.messaging.activemq;

import net.realmproject.dcm.messaging.DeviceMessage;
import net.realmproject.dcm.messaging.DeviceMessageReceiver;
import net.realmproject.dcm.messaging.activemq.AbstractActiveMQDeviceMessageReceiver;

/**
 * @author maxweld
 *
 */
public class DelegatingActiveMQDeviceMessageReceiver extends AbstractActiveMQDeviceMessageReceiver {

	private DeviceMessageReceiver deviceMessageReceiver;
	
	public DelegatingActiveMQDeviceMessageReceiver(String subject, boolean topic, String url) {
		super(subject, topic, url);
	}
	
	@Override
	public void receive(DeviceMessage<?> deviceMessage) {
		deviceMessageReceiver.receive(deviceMessage);
	}

	public void setDeviceMessageReceiver(DeviceMessageReceiver deviceMessageReceiver) {
		this.deviceMessageReceiver = deviceMessageReceiver;
	}
}
