package net.realmproject.dcm.accessor.test;


import java.io.Serializable;

import net.realmproject.dcm.accessor.impl.IDeviceWriter;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.bus.IDeviceEventBus;
import net.realmproject.dcm.features.command.Command;
import net.realmproject.dcm.util.DCMSerialize;

import org.junit.Test;


public class DeviceWriterUnitTest {

    @Test
    public void testDeviceWriter1() {
        System.out.println("testDeviceWriter1");

        Command command = DCMSerialize.deserialize(
                "{\"action\": \"anAction\",\"arguments\": {\"msg\": \"This is a test message!\"}}", Command.class);

        DeviceEventBus bus = new IDeviceEventBus();

        IDeviceWriter deviceWriter = new IDeviceWriter("id", bus);

        bus.subscribe(event -> {
            Serializable value = event.getValue();
            // System.out.println("2 " + testDevice.getValue().toString());
            System.out.println("1 " + DCMSerialize.serialize(value));
        });

        String label = deviceWriter.write(command);
        System.out.println(label); // What is lable?

    }

    @Test
    public void testDeviceWriter2() {
        System.out.println("testDeviceWriter2");

        Command command = DCMSerialize.deserialize(
                "{\"action\": \"anAction\",\"arguments\": {\"msg\": \"This is a test message!\"}}", Command.class);

        DeviceEventBus bus = new IDeviceEventBus();
        TestDeviceRecoder recorder = new TestDeviceRecoder();

        IDeviceWriter deviceWriter = new IDeviceWriter("deviceID", bus, recorder);

        bus.subscribe(event -> {
            Serializable value = event.getValue();
            // System.out.println("2 " + testDevice.getValue().toString());
            System.out.println("1 " + DCMSerialize.serialize(value));
        });

        String label = deviceWriter.write(command);

    }

}
