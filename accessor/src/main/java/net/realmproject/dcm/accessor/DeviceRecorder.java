package net.realmproject.dcm.accessor;


import java.io.Serializable;

import net.realmproject.dcm.command.Command;


public interface DeviceRecorder {

    String recordCommand(String deviceId, Command values);

    String recordState(String deviceId, Serializable state);

}
