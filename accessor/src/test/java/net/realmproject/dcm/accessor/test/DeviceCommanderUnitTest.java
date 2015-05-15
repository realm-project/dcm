package net.realmproject.dcm.accessor.test;


import static org.junit.Assert.assertEquals;
import net.realmproject.dcm.accessor.DeviceAccessor;
import net.realmproject.dcm.accessor.impl.IDeviceAccessor;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.bus.IDeviceEventBus;
import net.realmproject.dcm.features.command.Command;

import org.junit.Test;


public class DeviceCommanderUnitTest {

    static final String ID = "DeviceID";
    static final String MESSAGE = "This is a test message!";
    static final String ANOTHER_MESSAGE = "This is ANOTHER test message!";
    static final String COMMAND = "setMessage";

    @Test
    public void testDeviceReader1() {

        DeviceEventBus bus = new IDeviceEventBus();
        new TestAnnotatedCommandDevice(ID, bus);
        DeviceAccessor<TestState> deviceCommander = new IDeviceAccessor<>(ID, bus);

        deviceCommander.sendMessage(new Command(COMMAND).arg(MESSAGE));
        sleep();
        assertEquals(MESSAGE, deviceCommander.getState().getMessage());
    }

    @Test
    public void testDeviceReader2() {

        DeviceEventBus bus = new IDeviceEventBus();
        new TestAnnotatedCommandDevice(ID, bus);
        DeviceAccessor<TestState> deviceWriter = new IDeviceAccessor<>(ID, bus);

        deviceWriter.sendMessage(new Command(COMMAND).arg(MESSAGE));
        sleep();
        assertEquals(MESSAGE, deviceWriter.getState().getMessage());

        deviceWriter.sendMessage(new Command(COMMAND).arg(ANOTHER_MESSAGE));
        sleep();
        assertEquals(ANOTHER_MESSAGE, deviceWriter.getState().getMessage());
    }

    @Test
    public void testDeviceReader3() {

        DeviceEventBus bus = new IDeviceEventBus();
        new TestAnnotatedCommandDevice(ID, bus);
        DeviceAccessor<TestState> deviceWriter = new IDeviceAccessor<>(ID, bus);

        deviceWriter.sendMessage(new Command("setTwoMessages").arg("message", MESSAGE)
                .arg("secondMessage", ANOTHER_MESSAGE));
        sleep();
        assertEquals(MESSAGE, deviceWriter.getState().getMessage());
        assertEquals(ANOTHER_MESSAGE, deviceWriter.getState().getSecondMessage());
    }

    private void sleep() {
        try {
            Thread.sleep(200);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
