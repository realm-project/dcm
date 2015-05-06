package net.realmproject.dcm.accessor.test;


import java.util.HashMap;

import net.realmproject.dcm.command.Command;
import net.realmproject.dcm.command.CommandDevice;
import net.realmproject.dcm.command.CommandMethod;
import net.realmproject.dcm.event.bus.DeviceEventBus;

public class TestAnnotatedCommandDevice extends CommandDevice<TestState> {

    private TestState state;

    public TestAnnotatedCommandDevice(String id, DeviceEventBus bus) {
        super(id, bus);
        state = new TestState();
    }

    @Override
    protected TestState getState() {
        return state;
    }

    @CommandMethod
    public void setMessage(TestMessage myMessage) {
        state.setMessage(myMessage.msg);
    }

    // @CommandMethod
    // public void setTwoMessages(TestMessage firstMessage, TestMessage
    // secondMessage) {
    // state.setMessage(firstMessage.msg);
    // state.setSecondMessage(secondMessage.msg);
    // }

    @CommandMethod
    public void setTwoMessages(HashMap<String, String> messages) {
        state.setMessage(messages.get("message"));
        state.setSecondMessage(messages.get("secondMessage"));
    }

    @Override
    protected void afterCommand(Command command) {
        publish();
    }

}
