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
package net.realmproject.dcm.event;


/**
 * Represents the different kinds of messages which can be represented by a
 * {@link DeviceEvent}.
 * 
 * @author maxweld
 *
 */
public enum DeviceEventType {
    /**
     * An event indicating that a Device's value has changed
     */
    VALUE_CHANGED,

    /**
     * An event inteded to prompt a Device to re-emit it's current value or
     * state, even when no change has occurred. This is useful when a new node
     * is added to the graph.
     */
    VALUE_GET,

    /**
     * An event intended to set the value of a Device, assuming the device
     * supports simple value-setting interaction
     */
    VALUE_SET,

    /**
     * An event which contains an arbitrary message object in the
     * {@link DeviceEvent}'s payload. This kind of message enables arbitrary
     * messaging between nodes, and is the basis for higher-level APIs.
     */
    MESSAGE
}
