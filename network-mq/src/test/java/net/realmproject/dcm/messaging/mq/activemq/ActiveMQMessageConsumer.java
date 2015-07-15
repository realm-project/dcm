package net.realmproject.dcm.messaging.mq.activemq;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import net.realmproject.dcm.network.WireMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ActiveMQMessageConsumer implements Runnable {
	public void run() {
        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://208.75.74.33:61616");

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

//            connection.setExceptionListener(this);

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue("TEST.FOO");

            // Create a MessageConsumer from the Session to the Topic or Queue
            MessageConsumer consumer = session.createConsumer(destination);

            // Wait for 20 messages
            for (int i = 0; i < 20; i ++) {
            	try {
            		ObjectMessage objectMessage = (ObjectMessage) consumer.receive(1000);
            		try {
            			Serializable object = objectMessage.getObject();
            			try {
        					WireMessage deviceMessage = (WireMessage) object;
        					System.out.println("messageId of recieved message: " + deviceMessage.getMessageId());
        					System.out.println("EventType of recieved message's event: " + deviceMessage.getEvent().getDeviceEventType());
        				}
        				catch (ClassCastException e) {
        					System.err.println("Object class is not DeviceMessage");
        				}
            		}
                	catch (ClassCastException e) {
                		System.err.println("Object could not be unpackaged from ObjectMessage.");
                	}
            	}
            	catch (ClassCastException e) {
            		System.err.println("JMS Message class is not ObjectMessage.");
            	}
            	
            }

            consumer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
}
