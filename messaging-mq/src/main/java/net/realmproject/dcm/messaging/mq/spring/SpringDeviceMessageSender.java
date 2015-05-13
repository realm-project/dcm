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

package net.realmproject.dcm.messaging.mq.spring;


import javax.jms.Destination;

import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.messaging.DeviceMessage;
import net.realmproject.dcm.messaging.impl.IDeviceMessageSender;

import org.springframework.jms.core.JmsTemplate;


/**
 * @author maxweld
 *
 */
public class SpringDeviceMessageSender extends IDeviceMessageSender {

    private JmsTemplate jmsTemplate;
    private Destination destination;
    private String destinationName;

    public SpringDeviceMessageSender(DeviceEventBus bus) {
        super(bus);
    }

    public void send(DeviceMessage deviceMessage) {
        if (destination != null) {
            jmsTemplate.convertAndSend(destination, deviceMessage);
        } else if (destinationName != null) {
            jmsTemplate.convertAndSend(destinationName, deviceMessage);
        } else {
            jmsTemplate.convertAndSend(deviceMessage);
        }
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }
}
