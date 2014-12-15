package net.realmproject.device.core;


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

    @Override
    protected void afterCommand(Command command) {
        publish();
    }
}