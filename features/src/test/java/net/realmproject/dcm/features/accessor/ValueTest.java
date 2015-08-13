package net.realmproject.dcm.features.accessor;


import org.junit.Assert;
import org.junit.Test;

import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.bus.IDeviceEventBus;
import net.realmproject.dcm.features.value.IValueDevice;
import net.realmproject.dcm.features.value.ValueDevice;


public class ValueTest {

    @Test
    public void test1() throws InterruptedException {

        // Create an event bus to tie our nodes together
        DeviceEventBus bus = new IDeviceEventBus();

        // Create a simple value device which stores a single value with a
        // getter and setter. Set the value to "Hello"
        ValueDevice device = new IValueDevice("id", bus);
        device.setValue("Hello");

        // Create an accessor for this device
        DeviceAccessor<String> accessor = new IDeviceAccessor<>("id-accessor", "id", bus);
        Thread.sleep(10);
        // The accessor will query the device automatically on startup
        Assert.assertEquals("Hello", accessor.getState());
        // Send a SET message with the payload "World"
        accessor.sendValueSet("World");

        // Messages are delivered asynchronously
        Thread.sleep(10);

        Assert.assertEquals("World", device.getValue());
        Assert.assertEquals("World", accessor.getState());

    }

    @Test
    public void test2() throws InterruptedException {

        // Create an event bus to tie our nodes together
        DeviceEventBus bus = new IDeviceEventBus();

        // Create a simple value device which stores a single value with a
        // getter and setter. Set the value to "Hello"
        ValueDevice device = new IValueDevice("id", bus);
        device.setValue("Hello");

        // Create an accessor for this device
        DeviceAccessor<String> accessor = new IDeviceAccessor<>("id-accessor", "id", bus);
        // Thread.sleep(10);
        // The accessor will query the device automatically on startup
        // Assert.assertEquals("Hello", accessor.getState());
        // Send a SET message with the payload "World"
        accessor.sendValueSet("World");

        // Messages are delivered asynchronously
        Thread.sleep(10);

        Assert.assertEquals("World", device.getValue());
        Assert.assertEquals("World", accessor.getState());

    }

}
