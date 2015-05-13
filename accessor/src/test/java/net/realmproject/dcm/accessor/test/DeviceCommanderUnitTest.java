package net.realmproject.dcm.accessor.test;


import static org.junit.Assert.assertEquals;
import net.realmproject.dcm.accessor.commands.DeviceCommander;
import net.realmproject.dcm.accessor.commands.impl.IDeviceCommander;
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
        DeviceCommander deviceCommander = new IDeviceCommander(ID, bus);

        deviceCommander.write(new Command(COMMAND).arg(MESSAGE));
        sleep();
        assertEquals(MESSAGE, deviceCommander.getState().get("message"));
    }

    @Test
    public void testDeviceReader2() {

        DeviceEventBus bus = new IDeviceEventBus();
        new TestAnnotatedCommandDevice(ID, bus);
        DeviceCommander deviceWriter = new IDeviceCommander(ID, bus);

        deviceWriter.write(new Command(COMMAND).arg(MESSAGE));
        sleep();
        assertEquals(MESSAGE, deviceWriter.getState().get("message"));

        deviceWriter.write(new Command(COMMAND).arg(ANOTHER_MESSAGE));
        sleep();
        assertEquals(ANOTHER_MESSAGE, deviceWriter.getState().get("message"));
    }

    @Test
    public void testDeviceReader3() {

        DeviceEventBus bus = new IDeviceEventBus();
        new TestAnnotatedCommandDevice(ID, bus);
        DeviceCommander deviceWriter = new IDeviceCommander(ID, bus);

        deviceWriter.write(new Command("setTwoMessages").arg("message", MESSAGE).arg("secondMessage", ANOTHER_MESSAGE));
        sleep();
        assertEquals(MESSAGE, deviceWriter.getState().get("message"));
        assertEquals(ANOTHER_MESSAGE, deviceWriter.getState().get("secondMessage"));
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
