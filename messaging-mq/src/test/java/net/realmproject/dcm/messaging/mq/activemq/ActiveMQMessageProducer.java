package net.realmproject.dcm.messaging.mq.activemq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import net.realmproject.dcm.event.DeviceEventType;
import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.messaging.DeviceMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ActiveMQMessageProducer implements Runnable {

	@Override
	public void run() {
		try {
			// Create a ConnectionFactory
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://208.75.74.33:61616");

			// Create a Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();

			// Create a Session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create the destination (Topic or Queue)
			Destination destination = session.createQueue("TEST.FOO");

			// Create a MessageProducer from the Session to the Topic or Queue
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			// Create a DeviceMessage
			//String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
			//TextMessage message = session.createTextMessage(text);
			
			// Send 20 messages
			for (int i = 0; i < 20; i ++) {
				String messageId = Integer.toString(i);
				DeviceMessage dummyDeviceMessage = new DeviceMessage();
				dummyDeviceMessage.setMessageId(messageId);
				dummyDeviceMessage.setEvent(new IDeviceEvent(DeviceEventType.PING, "TestDevice"));
				ObjectMessage message = session.createObjectMessage(dummyDeviceMessage);
			
				producer.send(message);
				System.out.println("messageId of sent message: " + dummyDeviceMessage.getMessageId());
				Thread.sleep(500);
			}

			// Clean up
			session.close();
			connection.close();
		}
		catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}

	}

}
