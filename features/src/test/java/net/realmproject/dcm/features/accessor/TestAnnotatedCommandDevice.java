package net.realmproject.dcm.features.accessor;


import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.features.command.Arg;
import net.realmproject.dcm.features.command.Command;
import net.realmproject.dcm.features.command.CommandMethod;
import net.realmproject.dcm.features.command.ICommandDevice;


public class TestAnnotatedCommandDevice extends ICommandDevice<TestState> {

    private TestState state;

    public TestAnnotatedCommandDevice(String id, DeviceEventBus bus) {
        super(id, bus);
        state = new TestState();
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
