package net.realmproject.device.core;


import java.io.Serializable;
import java.util.HashMap;

import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.bus.IDeviceEventBus;
import net.realmproject.dcm.util.DCMSerialize;

import org.junit.Test;


public class DeviceUnitTest {

    @Test
    public void commandDeviceTest() {

        DeviceEventBus bus = new IDeviceEventBus();

        String jsonCommand = "{\"action\": \"setMessage\",\"arguments\": {\"msg\": \"This is a test message!\"}}";
        @SuppressWarnings("unchecked")
        HashMap<String, Object> mapCommand = (HashMap<String, Object>) DCMSerialize.deserialize(jsonCommand);
        TestAnnotatedCommandDevice testDevice = new TestAnnotatedCommandDevice("TestDevice", bus);

        bus.subscribe(event -> {
            Serializable value = event.getValue();
            // System.out.println("2 " + testDevice.getValue().toString());
            System.out.println("1 " + DCMSerialize.serialize(value));
        });
        testDevice.setValue(mapCommand);
    }
}
