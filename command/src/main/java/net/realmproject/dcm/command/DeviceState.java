package net.realmproject.dcm.command;


/**
 * Base class for {@link CommandDevice}s to report their device state
 * 
 * @author NAS
 *
 */
public class DeviceState {

    public enum Mode {
        IDLE, BUSY, ERROR
    }

    public Mode mode = Mode.IDLE;
}
