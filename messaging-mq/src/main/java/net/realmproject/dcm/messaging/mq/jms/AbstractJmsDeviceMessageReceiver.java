/**
 * Copyright 2014 Realm Project
 * 
 * This file is part of the Device Control Module (DCM).
 * 
 * DCM is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DCM is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DCM. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package net.realmproject.dcm.messaging.mq.jms;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import net.realmproject.dcm.event.Logging;
import net.realmproject.dcm.messaging.DeviceMessage;
import net.realmproject.dcm.messaging.DeviceMessageReceiver;

/**
 * @author maxweld
 *
 */
public abstract class AbstractJmsDeviceMessageReceiver implements MessageListener, DeviceMessageReceiver, Logging {

    public void onMessage(Message message) {
        try {
            ObjectMessage msg = (ObjectMessage) message;
            receive((DeviceMessage<?>) msg.getObject());
        }
        catch (JMSException e) {
        	getLog().warn(e.getMessage());
        }
        catch (ClassCastException e) {
        	getLog().warn("Received message does not contain a DeviceMessage.");
        }
    }

    public abstract void receive(DeviceMessage<?> deviceMessage);
}