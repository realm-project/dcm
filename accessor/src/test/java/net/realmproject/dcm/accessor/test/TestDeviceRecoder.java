package net.realmproject.dcm.accessor.test;

import java.io.Serializable;

import net.realmproject.dcm.accessor.impl.DummyDeviceRecorder;
import net.realmproject.dcm.features.command.Command;

public class TestDeviceRecoder extends DummyDeviceRecorder {
	
	public String recordCommand(String deviceId, Command values) {
		
		System.out.println("In TestDeviceRecorder.recordCommand");
		System.out.println("Device ID: " + deviceId);
		System.out.println("Action: " + values.action);
		System.out.println("Arguments: " + values.arguments);
		
		return deviceId;
	}

    public String recordState(String deviceId, Serializable state) {
    	
    	System.out.println("In TestDeviceRecorder.recordState");
		System.out.println("Device ID: " + deviceId);
		System.out.println("State: " + state.toString());
    	
		return deviceId;
	}

}
