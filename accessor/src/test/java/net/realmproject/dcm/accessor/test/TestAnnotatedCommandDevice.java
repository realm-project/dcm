package net.realmproject.dcm.accessor.test;


import net.realmproject.dcm.device.CommandDevice;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.features.command.Arg;
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
    public void setMessage(@Arg String myMessage) {
        state.setMessage(myMessage);
    }

    @CommandMethod
    public void setTwoMessages(@Arg("message") String first, @Arg("secondMessage") String second) {
        state.setMessage(first);
        state.setSecondMessage(second);
    }

    @Override
    public void afterCommand(Command command) {
        publishState();
    }

}
