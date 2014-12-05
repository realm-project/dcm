/** Copyright (c) Canadian Light Source, Inc. All rights reserved.
 *   - see license.txt for details.
 *
 *  Description:
 * 		SpringDeviceMessageSender class.
 */
package net.realmproject.dcm.messaging.spring;

import javax.jms.Destination;

import net.realmproject.dcm.messaging.DeviceMessage;
import net.realmproject.dcm.messaging.DeviceMessageSender;

import org.springframework.jms.core.JmsTemplate;

/**
 * @author maxweld
 *
 */
public class SpringDeviceMessageSender implements DeviceMessageSender {

	private JmsTemplate jmsTemplate;
	private Destination destination;
	private String destinationName;

	public void send(DeviceMessage<?> deviceMessage) {
		if(destination != null) {
			jmsTemplate.convertAndSend(destination, deviceMessage);
		}
		else if(destinationName != null) {
			jmsTemplate.convertAndSend(destinationName, deviceMessage);
		}
		else {
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
