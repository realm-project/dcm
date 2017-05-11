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

package net.realmproject.dcm.network.mq.activemq;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import net.realmproject.dcm.network.impl.IWireSource;
import net.realmproject.dcm.parcel.core.Logging;
import net.realmproject.dcm.parcel.core.hub.ParcelHub;

/**
 * @author maxweld
 *
 */
public class ActiveMQWireSource extends IWireSource implements Logging {

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


	public ActiveMQWireSource(ParcelHub bus, String subject, boolean topic, String url) {
		super(bus);
		this.subject = subject;
		this.topic = topic;
		this.url = url;
	}

	public boolean send(byte[] serializedParcel) {

		try {
			BytesMessage message = session.createBytesMessage();
			message.writeBytes(serializedParcel);

			try {
				messageProducer.send(message);

				if (transacted) {
					try {
						session.commit();
					} catch (JMSException e) {
						getLog().error("Exception while committing JMS Message", e);
						return false;
					}
				}
			} catch (JMSException e) {
				getLog().error("Exception while sending JMS BytesMessage", e);
				return false;
			}
		} catch (JMSException e) {
			getLog().error("Exception while creating JMS BytesMessage", e);
			return false;
		}

		return true;

	}

	public void connect() {
		try {
			if (!connected) {
				connectionFactory = new ActiveMQConnectionFactory(url);
				connection = connectionFactory.createConnection();
				connection.start();
				connected = true;
				session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
				if (topic) {
					destination = session.createTopic(subject);
				} else {
					destination = session.createQueue(subject);
				}

				messageProducer = session.createProducer(destination);
				if (persistent) {
					messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
				} else {
					messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				}
			}
		} catch (Exception e) {
			connected = false;
			getLog().error(e.getMessage());
		}
	}

	public void disconnect() {
		try {
			if (connected) {
				connection.close();
				connected = false;
			}
		} catch (Exception e) {
			connected = false;
			getLog().error(e.getMessage());
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
