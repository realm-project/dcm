/** Copyright (c) Canadian Light Source, Inc. All rights reserved.
 *   - see license.txt for details.
 *
 *  Description:
 * 		DeviceMessageSender interface.
 */
package net.realmproject.dcm.messaging;

import net.realmproject.dcm.messaging.DeviceMessage;

/** 
 * @author maxweld
 *
 */
public interface DeviceMessageSender {

	public void send(DeviceMessage<?> deviceMessage);
}
