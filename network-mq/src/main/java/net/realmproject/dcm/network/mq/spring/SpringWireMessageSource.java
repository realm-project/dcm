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

package net.realmproject.dcm.network.mq.spring;


import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;

import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.network.WireMessage;
import net.realmproject.dcm.network.impl.IWireMessageSource;
import net.realmproject.dcm.network.transcoder.IIdentityTranscoder;
import net.realmproject.dcm.network.transcoder.Transcoder;


/**
 * @author maxweld
 *
 */
public class SpringWireMessageSource extends IWireMessageSource {

    private JmsTemplate jmsTemplate;
    private Destination destination;
    private String destinationName;

    public SpringWireMessageSource(DeviceEventBus bus) {
        super(bus, new IIdentityTranscoder());
    }

    public SpringWireMessageSource(DeviceEventBus bus, Transcoder transcoder) {
        super(bus, transcoder);
    }

    public boolean send(WireMessage deviceMessage) {
        Object message = getTranscoder().encode(deviceMessage);
        if (destination != null) {
            jmsTemplate.convertAndSend(destination, message);
        } else if (destinationName != null) {
            jmsTemplate.convertAndSend(destinationName, message);
        } else {
            jmsTemplate.convertAndSend(message);
        }
        return true;
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
