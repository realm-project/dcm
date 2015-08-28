package net.realmproject.dcm.features.accessor;


import static org.junit.Assert.assertEquals;

import java.util.concurrent.BlockingQueue;

import org.junit.Test;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.bus.IDeviceEventBus;
import net.realmproject.dcm.event.filter.deviceeventtype.ValueChangedFilter;
import net.realmproject.dcm.features.command.Command;


public class DeviceCommanderUnitTest {

    static final String DEVICE_ID = "DeviceID";
    static final String ACCESSOR_ID = "AccessorID";
    static final String MESSAGE = "This is a test message!";
    static final String ANOTHER_MESSAGE = "This is ANOTHER test message!";
    static final String COMMAND = "setMessage";

    @Test
    public void testDeviceReader1() throws InterruptedException {

        DeviceEventBus bus = new IDeviceEventBus();
        BlockingQueue<DeviceEvent> eventQueue = bus.subscriptionQueue(new ValueChangedFilter());

        new TestAnnotatedCommandDevice(DEVICE_ID, bus);
        DeviceAccessor<TestState> deviceCommander = new IDeviceAccessor<>(ACCESSOR_ID, DEVICE_ID, bus);

        // initial CHANGED message
        eventQueue.take();

        deviceCommander.sendMessage(new Command(COMMAND).property(MESSAGE));

        eventQueue.take();
        sleep();
        System.out.println("ASSERTING!");
        assertEquals(MESSAGE, deviceCommander.getState().getMessage());

    }

    @Test
    public void testDeviceReader2() throws InterruptedException {

        DeviceEventBus bus = new IDeviceEventBus();
        BlockingQueue<DeviceEvent> eventQueue = bus.subscriptionQueue(new ValueChangedFilter());

        new TestAnnotatedCommandDevice(DEVICE_ID, bus);
        DeviceAccessor<TestState> deviceWriter = new IDeviceAccessor<>(ACCESSOR_ID, DEVICE_ID, bus);

        // initial CHANGED message
        eventQueue.take();

        deviceWriter.sendMessage(new Command(COMMAND).property(MESSAGE));
        eventQueue.take();
        sleep();
        assertEquals(MESSAGE, deviceWriter.getState().getMessage());

        deviceWriter.sendMessage(new Command(COMMAND).property(ANOTHER_MESSAGE));
        eventQueue.take();
        sleep();
        assertEquals(ANOTHER_MESSAGE, deviceWriter.getState().getMessage());

    }

    @Test
    public void testDeviceReader3() throws InterruptedException {

        DeviceEventBus bus = new IDeviceEventBus();
        BlockingQueue<DeviceEvent> eventQueue = bus.subscriptionQueue(new ValueChangedFilter());

        new TestAnnotatedCommandDevice(DEVICE_ID, bus);
        DeviceAccessor<TestState> deviceWriter = new IDeviceAccessor<>(ACCESSOR_ID, DEVICE_ID, bus);

        // initial CHANGED message
        eventQueue.take();

        deviceWriter.sendMessage(
                new Command("setTwoMessages").property("message", MESSAGE).property("secondMessage", ANOTHER_MESSAGE));

        eventQueue.take();
        sleep();
        assertEquals(MESSAGE, deviceWriter.getState().getMessage());
        assertEquals(ANOTHER_MESSAGE, deviceWriter.getState().getSecondMessage());

    }

    private void sleep() throws InterruptedException {
        Thread.sleep(100);
    }

}
