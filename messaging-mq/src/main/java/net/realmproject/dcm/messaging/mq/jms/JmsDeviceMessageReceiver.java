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
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.messaging.DeviceMessage;
import net.realmproject.dcm.messaging.Transcoder;
import net.realmproject.dcm.messaging.impl.IDeviceMessageReceiver;
import net.realmproject.dcm.messaging.transcoders.IIdentityTranscoder;


/**
 * @author maxweld
 *
 */
public class JmsDeviceMessageReceiver extends IDeviceMessageReceiver implements MessageListener, Logging {

    public JmsDeviceMessageReceiver(DeviceEventBus bus) {
        super(bus, new IIdentityTranscoder());
    }

    public JmsDeviceMessageReceiver(DeviceEventBus bus, Transcoder transcoder) {
        super(bus, transcoder);
    }

    public void onMessage(Message message) {
        try {
            ObjectMessage msg = (ObjectMessage) message;
            transcoder.decode(msg.getObject());
            receive((DeviceMessage) msg.getObject());
        }
        catch (JMSException e) {
            getLog().warn(e.getMessage());
        }
        catch (ClassCastException e) {
            getLog().warn("Received message does not contain a DeviceMessage.");
        }
    }

}
