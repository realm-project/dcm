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

package net.realmproject.dcm.messaging.mq.activemq;


import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import net.realmproject.dcm.event.Logging;
import net.realmproject.dcm.messaging.DeviceMessage;
import net.realmproject.dcm.messaging.DeviceMessageSender;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author maxweld
 *
 */
public class ActiveMQDeviceMessageSender implements DeviceMessageSender, Logging {

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

                if (transacted) {
                    try {
                        session.commit();
                    }
                    catch (JMSException e) {
                        getLog().error("Exception while committing JMS Message", e);
                    }
                }
            }
            catch (JMSException e) {
            	getLog().error("Exception while sending JMS ObjectMessage", e);
            }
        }
        catch (JMSException e) {
        	getLog().error("Exception while creating JMS ObjectMessage", e);
        }
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
