package net.realmproject.dcm.device;


/**
 * Base exception for {@link Device}s. All exceptions must include the id of the
 * device of origin.
 * 
 * @author NAS
 *
 */
public class DeviceException extends RuntimeException {

    private static final long serialVersionUID = -2848959217841451950L;

    private String deviceId;

    public DeviceException(String deviceId, String message) {
        super(message);
        this.deviceId = deviceId;
    }

    public DeviceException(String deviceId, Throwable cause) {
        super(cause);
        this.deviceId = deviceId;
    }

    public DeviceException(String deviceId, Throwable cause, String message) {
        super(message, cause);
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

}
