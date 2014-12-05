package net.realmproject.dcm.accessor;


import java.io.Serializable;
import java.util.Date;
import java.util.Map;


public interface DeviceReader extends DeviceAccessor {

    Map<String, Serializable> getState();

    Date getTimestamp();

    void query();
}
