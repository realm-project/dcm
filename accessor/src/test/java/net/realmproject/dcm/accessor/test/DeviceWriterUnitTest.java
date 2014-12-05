package net.realmproject.dcm.accessor.test;

import java.io.Serializable;

import org.junit.Test;

import net.realmproject.dcm.accessor.impl.IDeviceWriter;
import net.realmproject.dcm.command.Command;
import net.realmproject.dcm.command.CommandSerialize;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.bus.IDeviceEventBus;
import net.realmproject.util.RealmSerialize;

public class DeviceWriterUnitTest {
	
	@Test
	public void testDeviceWriter1() {
		System.out.println("testDeviceWriter1");
		
		Command command = RealmSerialize.deserialize("{\"action\": \"anAction\",\"arguments\": {\"msg\": \"This is a test message!\"}}", Command.class);
		
		DeviceEventBus bus = new IDeviceEventBus();
		
		IDeviceWriter deviceWriter = new IDeviceWriter("id", bus);
		
		bus.subscribe(event -> {
            Serializable value = event.getValue();
            // System.out.println("2 " + testDevice.getValue().toString());
            System.out.println("1 " + CommandSerialize.serialize(value));
        });
		
		String label = deviceWriter.write(command);
		System.out.println(label); //What is lable?
		
	}
	
	@Test
	public void testDeviceWriter2() {
		System.out.println("testDeviceWriter2");
		
		Command command = RealmSerialize.deserialize("{\"action\": \"anAction\",\"arguments\": {\"msg\": \"This is a test message!\"}}", Command.class);
		
		DeviceEventBus bus = new IDeviceEventBus();
		TestDeviceRecoder recorder = new TestDeviceRecoder();
		
		IDeviceWriter deviceWriter = new IDeviceWriter("deviceID", bus, recorder);
				
		bus.subscribe(event -> {
            Serializable value = event.getValue();
            // System.out.println("2 " + testDevice.getValue().toString());
            System.out.println("1 " + CommandSerialize.serialize(value));
        });
		
		String label = deviceWriter.write(command);
		
	}
	
}
