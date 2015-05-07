package net.realmproject.dcm.device.test;

import net.realmproject.dcm.features.Statefulness.State;




public class TestState extends State {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }
}
