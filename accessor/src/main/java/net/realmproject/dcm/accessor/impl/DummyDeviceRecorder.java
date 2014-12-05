package net.realmproject.dcm.accessor.impl;


import java.io.Serializable;

import net.realmproject.dcm.accessor.DeviceRecorder;
import net.realmproject.dcm.command.Command;


public class DummyDeviceRecorder implements DeviceRecorder {


    @Override
    public String recordCommand(String deviceId, Command command) {
        return null;
    }

    @Override
    public String recordState(String deviceId, Serializable state) {
        return null;
    }

}
