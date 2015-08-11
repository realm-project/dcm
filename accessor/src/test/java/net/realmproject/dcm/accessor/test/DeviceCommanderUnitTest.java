package net.realmproject.dcm.accessor.test;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.realmproject.dcm.accessor.DeviceAccessor;
import net.realmproject.dcm.accessor.impl.IDeviceAccessor;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.bus.IDeviceEventBus;
import net.realmproject.dcm.features.command.Command;


public class DeviceCommanderUnitTest {

    static final String ID = "DeviceID";
    static final String MESSAGE = "This is a test message!";
    static final String ANOTHER_MESSAGE = "This is ANOTHER test message!";
    static final String COMMAND = "setMessage";

    @Test
    public void testDeviceReader1() throws InterruptedException {

        DeviceEventBus bus = new IDeviceEventBus();
        new TestAnnotatedCommandDevice(ID, bus);
        DeviceAccessor<TestState> deviceCommander = new IDeviceAccessor<>(ID, ID, bus);

        deviceCommander.sendMessage(new Command(COMMAND).property(MESSAGE));
        Thread.sleep(1000);
        assertEquals(MESSAGE, deviceCommander.getState().getMessage());
    }

    @Test
    public void testDeviceReader2() throws InterruptedException {

        DeviceEventBus bus = new IDeviceEventBus();
        new TestAnnotatedCommandDevice(ID, bus);
        DeviceAccessor<TestState> deviceWriter = new IDeviceAccessor<>(ID, ID, bus);

        deviceWriter.sendMessage(new Command(COMMAND).property(MESSAGE));
        Thread.sleep(1000);
        assertEquals(MESSAGE, deviceWriter.getState().getMessage());

        deviceWriter.sendMessage(new Command(COMMAND).property(ANOTHER_MESSAGE));
        Thread.sleep(1000);
        assertEquals(ANOTHER_MESSAGE, deviceWriter.getState().getMessage());
    }

    @Test
    public void testDeviceReader3() throws InterruptedException {

        DeviceEventBus bus = new IDeviceEventBus();
        new TestAnnotatedCommandDevice(ID, bus);
        DeviceAccessor<TestState> deviceWriter = new IDeviceAccessor<>(ID, ID, bus);

        deviceWriter.sendMessage(
                new Command("setTwoMessages").property("message", MESSAGE).property("secondMessage", ANOTHER_MESSAGE));
        Thread.sleep(1000);
        assertEquals(MESSAGE, deviceWriter.getState().getMessage());
        assertEquals(ANOTHER_MESSAGE, deviceWriter.getState().getSecondMessage());
    }

}
