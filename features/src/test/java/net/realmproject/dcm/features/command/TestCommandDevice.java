package net.realmproject.dcm.features.command;


import net.realmproject.dcm.features.command.Arg;
import net.realmproject.dcm.features.command.CommandMethod;
import net.realmproject.dcm.features.command.ICommandDevice;
import net.realmproject.dcm.features.statefulness.TestState;
import net.realmproject.dcm.parcel.core.flow.hub.ParcelHub;


public class TestCommandDevice extends ICommandDevice<TestState> {

    TestState state = new TestState();

    public TestCommandDevice(String id, ParcelHub bus) {
        super(id, bus);
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