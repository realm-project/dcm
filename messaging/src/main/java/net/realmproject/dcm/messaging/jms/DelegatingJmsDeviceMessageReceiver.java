/** Copyright (c) Canadian Light Source, Inc. All rights reserved.
 *   - see license.txt for details.
 *
 *  Description:
 * 		DelegatingJmsDeviceMessageReceiver class.
 * 
 */
package net.realmproject.dcm.messaging.jms;

import net.realmproject.dcm.messaging.DeviceMessage;
import net.realmproject.dcm.messaging.DeviceMessageReceiver;

/**
 * @author maxweld
 *
 */
public class DelegatingJmsDeviceMessageReceiver extends AbstractJmsDeviceMessageReceiver {

	private DeviceMessageReceiver deviceMessageReceiver;
	
	@Override
	public void receive(DeviceMessage<?> deviceMessage) {
		deviceMessageReceiver.receive(deviceMessage);
	}

	public DeviceMessageReceiver getDeviceMessageReceiver() {
		return deviceMessageReceiver;
	}
	public void setDeviceMessageReceiver(DeviceMessageReceiver deviceMessageReceiver) {
		this.deviceMessageReceiver = deviceMessageReceiver;
	}
}
