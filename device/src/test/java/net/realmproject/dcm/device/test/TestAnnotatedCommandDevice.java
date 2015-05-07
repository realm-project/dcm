package net.realmproject.dcm.device.test;


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

    @Override
    public void afterCommand(Command command) {
        publishState();
    }

}
