package net.realmproject.dcm.device.test.commands;


import net.realmproject.dcm.device.CommandDevice;
import net.realmproject.dcm.device.test.statefulness.TestState;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.features.command.Arg;
import net.realmproject.dcm.features.command.CommandMethod;


public class TestCommandDevice extends CommandDevice<TestState> {

    TestState state = new TestState();

    public TestCommandDevice(String id, DeviceEventBus bus) {
        super(id, bus);
        initCommands(bus);
    }

    @Override
    public TestState getState() {
        return state;
    }

    @CommandMethod
    public void increment() {
        state.number++;
    }

    @CommandMethod
    public void incrementBy(@Arg("value") int value) {
        state.number += value;
    }

    @CommandMethod
    public void incrementFromString(@Arg("value") String value) {
        state.number += Integer.parseInt(value);
    }

    @CommandMethod
    public void incrementByIncrementer(@Arg("value") Incrementer incrementer) {
        state.number += incrementer.increment;
    }

    @CommandMethod
    public void incrementByIncrementerUnannotated(Incrementer incrementer) {
        state.number += incrementer.increment;
    }

    @CommandMethod
    public void incrementTwice(@Arg("first") int first, @Arg("second") String second) {
        state.number += first;
        state.number += Integer.parseInt(second);
    }

    @CommandMethod
    public void incrementUnnamed(@Arg int inc) {
        state.number += inc;
    }

}