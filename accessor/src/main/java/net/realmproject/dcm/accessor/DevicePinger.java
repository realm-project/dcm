package net.realmproject.dcm.accessor;




public interface DevicePinger extends DeviceAccessor {

    /**
     * Performs a ping and returns immediately
     */
    void ping();

}
