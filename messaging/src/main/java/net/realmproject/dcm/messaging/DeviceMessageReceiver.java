/** Copyright (c) Canadian Light Source, Inc. All rights reserved.
 *   - see license.txt for details.
 *
 *  Description:
 * 		DeviceMessageReceiver interface.
 */
package net.realmproject.dcm.messaging;

import net.realmproject.dcm.messaging.DeviceMessage;

/**
 * @author maxweld
 *
 */
public interface DeviceMessageReceiver {

	public void receive(DeviceMessage<?> deviceMessage);
}
