package net.realmproject.dcm.accessor;


import net.realmproject.dcm.features.Identity;


public interface DevicePinger extends Identity {

    /**
     * Performs a ping and returns immediately
     */
    void ping();

}
