/**
 * Copyright 2014 Realm Project
 * 
 * This file is part of the Device Control Module (DCM).
 * 
 * DCM is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DCM is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DCM. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

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
