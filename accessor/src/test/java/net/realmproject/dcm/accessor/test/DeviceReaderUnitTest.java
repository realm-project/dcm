package net.realmproject.dcm.accessor.test;


import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.util.Map;

import net.realmproject.dcm.accessor.commands.DeviceCommander;
import net.realmproject.dcm.accessor.commands.impl.IDeviceCommander;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.bus.IDeviceEventBus;
import net.realmproject.dcm.features.command.Command;

import org.junit.Test;


public class DeviceReaderUnitTest {

    @Test
    public void testDeviceReader1() {
        System.out.println("testDeviceReader1");

        String id = "DeviceID";
        String message = "This is a test message!";
        Command command = new Command();
        command.action = "setMessage";
        command.arguments.put("msg", message);

        DeviceEventBus bus = new IDeviceEventBus();
        TestAnnotatedCommandDevice device = new TestAnnotatedCommandDevice(id, bus);
        DeviceCommander deviceCommander = new IDeviceCommander(id, bus);

        Map<String, Serializable> deviceReaderState = deviceCommander.getState();
        System.out.println("deviceReaderState before write: " + deviceReaderState);

        deviceCommander.write(command);

        try {
            Thread.currentThread();
            Thread.sleep(500);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        deviceReaderState = deviceCommander.getState();
        System.out.println("deviceReaderState after write: " + deviceReaderState);

        assertEquals(message, deviceCommander.getState().get("message"));
    }

    @Test
    public void testDeviceReader2() {
        System.out.println("testDeviceReader2");

        String id = "DeviceID";
        String message = "This is a test message!";
        String anotherMessage = "This is ANOTHER test message!";

        Command command = new Command();

        DeviceEventBus bus = new IDeviceEventBus();
        TestAnnotatedCommandDevice device = new TestAnnotatedCommandDevice(id, bus);
        DeviceCommander deviceWriter = new IDeviceCommander(id, bus);

        Map<String, Serializable> deviceReaderState = deviceWriter.getState();
        System.out.println("deviceReaderState before write: " + deviceReaderState);

        command.action = "setMessage";
        command.arguments.put("msg", message);

        deviceWriter.write(command);

        try {
            Thread.currentThread();
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        command.arguments.put("msg", anotherMessage);

        deviceWriter.write(command);

        try {
            Thread.currentThread();
            Thread.sleep(500);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        deviceReaderState = deviceWriter.getState();
        System.out.println("deviceReaderState after write: " + deviceReaderState);

        assertEquals(anotherMessage, deviceWriter.getState().get("message"));
    }

    @Test
    public void testDeviceReader3() {
        System.out.println("testDeviceReader3");

        String id = "DeviceID";
        String firstMessage = "This is FIRST test message!";
        String secondMessage = "This is SECOND test message!";

        Command command = new Command();

        DeviceEventBus bus = new IDeviceEventBus();
        TestAnnotatedCommandDevice device = new TestAnnotatedCommandDevice(id, bus);
        DeviceCommander deviceWriter = new IDeviceCommander(id, bus);

        Map<String, Serializable> deviceReaderState = deviceWriter.getState();
        System.out.println("deviceReaderState before write: " + deviceReaderState);

        command.action = "setTwoMessages";
        command.arguments.put("message", firstMessage);
        command.arguments.put("secondMessage", secondMessage);

        deviceWriter.write(command);

        try {
            Thread.currentThread();
            Thread.sleep(500);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        deviceReaderState = deviceWriter.getState();
        System.out.println("deviceReaderState after write: " + deviceReaderState);

        assertEquals(command.arguments.get("message"), deviceWriter.getState().get("message"));
        assertEquals(command.arguments.get("secondMessage"), deviceWriter.getState().get("secondMessage"));
    }

}
