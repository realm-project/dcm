package net.realmproject.dcm.device.test;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.bus.IDeviceEventBus;
import net.realmproject.dcm.features.command.Command;
import net.realmproject.dcm.util.DCMSerialize;

import org.junit.Assert;
import org.junit.Test;


public class DeviceUnitTest {

    @Test
    public void commandDeviceTest() throws InterruptedException {

    	final String TEST_MESSAGE = "Test Message";
    	
        DeviceEventBus bus = new IDeviceEventBus();

        Command command = new Command();
        command.action = "setMessage";
        command.arguments = new HashMap<>();
        command.arguments.put("msg", TEST_MESSAGE);
        
        TestAnnotatedCommandDevice testDevice = new TestAnnotatedCommandDevice("TestDevice", bus);

        //listen for events, compare message to test message
        //throwing exceptions in lambdas is wonky, so catch it, 
        //put it in a list, and assert that the list is empty later
        List<Throwable> exceptions = new ArrayList<>();
        bus.subscribe(event -> {
            Serializable value = event.getValue();
            TestState state = DCMSerialize.convertMessage(value, TestState.class);
            System.out.println("1 " + state.getMessage());
            try {
            	Assert.assertEquals(TEST_MESSAGE, state.getMessage());
            } catch (Throwable t) {
            	t.printStackTrace();
            	exceptions.add(t);
            }
        });
        
        testDevice.runCommand(command);
        Thread.sleep(500);
        Assert.assertEquals(0, exceptions.size());
    }
}
