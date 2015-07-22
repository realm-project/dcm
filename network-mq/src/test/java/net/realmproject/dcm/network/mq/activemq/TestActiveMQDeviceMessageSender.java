package net.realmproject.dcm.network.mq.activemq;

import net.realmproject.dcm.network.mq.activemq.ActiveMQWireMessageSource;

public class TestActiveMQDeviceMessageSender extends ActiveMQWireMessageSource {

	public TestActiveMQDeviceMessageSender(String subject, boolean topic, String url) {
		super(null, subject, topic, url);
	}
	
	public void send() {
		//TODO		
		for (int i = 0; i < 20; i ++)
			super.send(null); // Pass a device message to it.
	}
	
	public static void main(String[] args) {

		thread(new ActiveMQMessageConsumer());

		TestActiveMQDeviceMessageSender sender = new TestActiveMQDeviceMessageSender("TEST.FOO", false, "tcp://208.75.74.33:61616");
		sender.connect();
		sender.send();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		sender.disconnect();
	}
	
	public static void thread(Runnable runnable) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(false);
		brokerThread.start();
	}
}
