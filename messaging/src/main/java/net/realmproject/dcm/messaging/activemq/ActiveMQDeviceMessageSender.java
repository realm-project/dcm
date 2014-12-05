/** Copyright (c) Canadian Light Source, Inc. All rights reserved.
 *   - see license.txt for details.
 *
 *  Description:
 * 		ActiveMQDeviceMessageSender class.
 * 
 */
package net.realmproject.dcm.messaging.activemq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import net.realmproject.dcm.messaging.DeviceMessage;
import net.realmproject.dcm.messaging.DeviceMessageSender;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author maxweld
 *
 */
public class ActiveMQDeviceMessageSender implements DeviceMessageSender {
	
	protected Log logger = LogFactory.getLog(getClass()); 
	
	public String url;
	public String subject;
	public boolean topic;
	
	protected boolean transacted = false;
	protected boolean persistent = false;
	protected boolean connected = false;
	
	protected ActiveMQConnectionFactory connectionFactory;
	protected MessageProducer messageProducer;
	protected Destination destination;
	protected Connection connection;
	protected Session session;
	
	public ActiveMQDeviceMessageSender(String subject, boolean topic, String url) {
		this.subject = subject;
		this.topic = topic;
		this.url = url;
	}
	
	public void send(DeviceMessage<?> deviceMessage) {
		
		try {
			ObjectMessage message = session.createObjectMessage(deviceMessage);
			
			try {
				messageProducer.send(message);	
				
				if(transacted) {
					try { 
						session.commit();
					} 
					catch(JMSException e) {
						logger.error("Exception while committing JMS Message", e);
					}
				}
			}
			catch(JMSException e) {
				logger.error("Exception while sending JMS ObjectMessage", e);
			}
		} 
		catch(JMSException e) {
			logger.error("Exception while creating JMS ObjectMessage", e);
		}
	}
	
	public void connect() {
		try {
			if(!connected) {
				connectionFactory = new ActiveMQConnectionFactory(url);
				connection = connectionFactory.createConnection();
				connection.start();
				connected = true;
				session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
				if(topic) {
					destination = session.createTopic(subject);
				} else {
					destination = session.createQueue(subject);
				}
				
				messageProducer = session.createProducer(destination);
				if(persistent) {
					messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
				} else {
					messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				}
			}
		} catch (Exception e) {
			connected = false;
			logger.error(e.getMessage());
		}
	}
	
	public void disconnect() {
		try {
			if(connected) {
				connection.close();
				connected = false;
			}
		} catch (Exception e) {
			connected = false;
			logger.error(e.getMessage());
		}
	}

	public String getUrl() {
		return this.url;
	}

	public String getSubject() {
		return this.subject;
	}

	public boolean isTopic() {
		return this.topic;
	}
}
