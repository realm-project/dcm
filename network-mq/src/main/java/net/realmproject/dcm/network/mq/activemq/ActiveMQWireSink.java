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
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import net.realmproject.dcm.network.impl.IWireSink;
import net.realmproject.dcm.parcel.core.Logging;
import net.realmproject.dcm.parcel.core.ParcelReceiver;

/**
 * @author maxweld
 *
 */
public class ActiveMQWireSink extends IWireSink implements MessageListener, Logging {

	private String url;
	private String subject;
	private boolean topic;
	private String username;
	private String password;

	protected boolean connected = false;
	protected boolean transacted = false;

	protected ActiveMQConnectionFactory connectionFactory;
	protected MessageConsumer messageConsumer;
	protected Connection connection;
	protected Session session;

	public ActiveMQWireSink(ParcelReceiver receiver, String subject, boolean topic, String url) {
		this(receiver, subject, topic, url, null, null);
	}

	public ActiveMQWireSink(ParcelReceiver receiver, String subject, boolean topic, String url, String username,
			String password) {
		super(receiver);
		this.subject = subject;
		this.topic = topic;
		this.url = url;
		this.username = username;
		this.password = password;

		connect();
	}

	public void onMessage(Message message) {

		try {
			receive(readMessage(message));
		} catch (JMSException e) {
			getLog().error("Serializable content could not be unpacked from JMS Message", e);
		} catch (ClassCastException e) {
			getLog().error("JMS Message class is not BytesMessage.", e);
		}
	}

	private byte[] readMessage(Message message) throws JMSException {

		if (message instanceof BytesMessage) {
			BytesMessage bytesMessage = (BytesMessage) message;
			int length = (int) bytesMessage.getBodyLength();
			byte[] buffer = new byte[length];
			bytesMessage.readBytes(buffer);
			return buffer;
		}

		throw new ClassCastException();

	}

	public void connect() {
		try {
			if (!connected) {
				if (username != null) {
					connectionFactory = new ActiveMQConnectionFactory(username, password, url);
				} else {
					connectionFactory = new ActiveMQConnectionFactory(url);
				}
				connection = connectionFactory.createConnection();
				connection.start();
				connected = true;

				session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
				if (this.topic) {
					Topic topic = session.createTopic(subject);
					messageConsumer = session.createConsumer(topic);
				} else {
					Queue queue = session.createQueue(subject);
					messageConsumer = session.createConsumer(queue);
				}
				messageConsumer.setMessageListener(this);

				getLog().info(this.getClass().getSimpleName() + " waiting for messages on " + url);
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
