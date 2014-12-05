package net.realmproject.device.core;


import net.realmproject.dcm.command.DeviceState;


public class TestState extends DeviceState {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }
}
