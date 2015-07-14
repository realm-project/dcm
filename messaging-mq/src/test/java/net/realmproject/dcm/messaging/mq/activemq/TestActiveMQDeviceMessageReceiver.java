package net.realmproject.dcm.messaging.mq.activemq;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import net.realmproject.dcm.messaging.DeviceMessage;

public class TestActiveMQDeviceMessageReceiver extends ActiveMQDeviceMessageReceiver {

	public TestActiveMQDeviceMessageReceiver(String subject, boolean topic, String url) {
		super(null, subject, topic, url);
	}

	public void onMessage(Message message) {
		try {
			ObjectMessage objectMessage = (ObjectMessage) message;
			try {
				Serializable object = objectMessage.getObject();
				try {
					DeviceMessage deviceMessage = (DeviceMessage) object;
					System.out.println("messageId of recieved message: " + deviceMessage.getMessageId());
					System.out.println("EventType of recieved message's event: " + deviceMessage.getEvent().getDeviceEventType());
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

	public static void main(String[] args) {

		thread(new ActiveMQMessageProducer());

		TestActiveMQDeviceMessageReceiver receiver = new TestActiveMQDeviceMessageReceiver("TEST.FOO", false, "tcp://208.75.74.33:61616");
		receiver.connect();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		receiver.disconnect();
	}

	public static void thread(Runnable runnable) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(false);
		brokerThread.start();
	}

}
