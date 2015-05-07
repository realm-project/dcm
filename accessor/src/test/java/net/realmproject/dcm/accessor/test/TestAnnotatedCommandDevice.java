package net.realmproject.dcm.accessor.test;


import java.util.HashMap;

import net.realmproject.dcm.device.CommandDevice;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.features.command.Command;
import net.realmproject.dcm.features.command.CommandMethod;

public class TestAnnotatedCommandDevice extends CommandDevice<TestState> {

    private TestState state;

    public TestAnnotatedCommandDevice(String id, DeviceEventBus bus) {
        super(id, bus);
        state = new TestState();
        initCommands(bus);
    }

    @Override
    public TestState getState() {
        return state;
    }
    
    @CommandMethod
    public void setMessage(TestMessage myMessage) {
        state.setMessage(myMessage.msg);
    }

    @CommandMethod
    public void setTwoMessages(HashMap<String, String> messages) {
        state.setMessage(messages.get("message"));
        state.setSecondMessage(messages.get("secondMessage"));
    }


    @Override
    public void afterCommand(Command command) {
        publishState();
    }

}
