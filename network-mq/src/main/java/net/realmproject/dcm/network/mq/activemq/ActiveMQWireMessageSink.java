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


import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import net.realmproject.dcm.event.Logging;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.receiver.DeviceEventReceiver;
import net.realmproject.dcm.network.WireMessage;
import net.realmproject.dcm.network.impl.IWireMessageSink;
import net.realmproject.dcm.network.transcoder.IIdentityTranscoder;
import net.realmproject.dcm.network.transcoder.Transcoder;


/**
 * @author maxweld
 *
 */
public class ActiveMQWireMessageSink extends IWireMessageSink implements MessageListener, Logging {

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

    public ActiveMQWireMessageSink(DeviceEventReceiver receiver, String subject, boolean topic, String url) {
        this(receiver, new IIdentityTranscoder(), subject, topic, url);
    }

    public ActiveMQWireMessageSink(DeviceEventReceiver receiver, Transcoder<WireMessage, Serializable> transcoder, String subject, boolean topic,
            String url) {
        this(receiver, new IIdentityTranscoder(), subject, topic, url, null, null);
    }
    
    public ActiveMQWireMessageSink(DeviceEventReceiver receiver, String subject, boolean topic, String url, String username, String password) {
        this(receiver, new IIdentityTranscoder(), subject, topic, url, username, password);
    }
    
    public ActiveMQWireMessageSink(DeviceEventReceiver receiver, Transcoder<WireMessage, Serializable> transcoder, String subject, boolean topic, String url, String username, String password) {
    	super(receiver, transcoder);
    	this.subject = subject;
        this.topic = topic;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            try {
                Serializable object = objectMessage.getObject();
                try {
                    receive(getTranscoder().decode(object));
                }
                catch (ClassCastException e) {
                    getLog().error("Object class is not DeviceMessage", e);
                }
            }
            catch (JMSException e) {
                getLog().error("Object could not be unpackaged from ObjectMessage", e);
            }
        }
        catch (ClassCastException e) {
            getLog().error("JMS Message class is not ObjectMessage.", e);
        }
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
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {
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
