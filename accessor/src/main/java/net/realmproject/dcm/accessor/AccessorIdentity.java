package net.realmproject.dcm.accessor;

import net.realmproject.dcm.event.identity.Identity;

public interface AccessorIdentity extends Identity {

    String getDeviceId();

    void setDeviceId(String deviceId);

}
