package net.realmproject.dcm.accessor.test;


import net.realmproject.dcm.command.DeviceState;


public class TestState extends DeviceState {

    private String message;
    private String secondMessage;

    public String getSecondMessage() {
		return secondMessage;
	}

	public void setSecondMessage(String secondMessage) {
		this.secondMessage = secondMessage;
	}

	public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }
    
    public void setTwoMessages(String firstMessage, String secondMessage) {
        setMessage(firstMessage);
        setSecondMessage(secondMessage);
    }
}
