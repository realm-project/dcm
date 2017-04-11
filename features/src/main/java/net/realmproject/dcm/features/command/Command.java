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

package net.realmproject.dcm.features.command;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.realmproject.dcm.event.identity.Identity;
import net.realmproject.dcm.features.Properties;
import net.realmproject.dcm.features.recording.Recordable;
import net.realmproject.dcm.util.DCMSerialize;
import net.realmproject.dcm.util.DCMUtil;


/**
 * Class representing a command issued to a device. A command is similar to a
 * method invocation (or a smalltalk message). It contains an action name
 * analogous to method name (or selector), and arguments/properties analogous to
 * named arguments (or keywords). Devices implementing the {@link CommandDevice}
 * interface can apply the {@link CommandMethod} and {@link Arg} annotations to
 * methods to enable automatic dispatch of commands to methods
 * 
 * @author NAS
 *
 */
public class Command implements Properties<Object>, Recordable, Identity {

    /**
     * The name of the action or command to invoke.
     */
    private String action;

    private String id = DCMUtil.generateId(getClass());
    private boolean toRecord;

    /**
     * The arguments to invoke the command with
     */
    private Map<String, Object> properties = new HashMap<>();

    public Command() {}

    public Command(String action) {
        this();
        this.action = action;
    }

    public Command(String action, Map<String, ? extends Object> args) {
        this();
        this.action = action;
        this.getProperties().putAll(args);
    }

    /**
     * Adds the given argument to the Command with the given key as the argument
     * name
     * 
     * @param key
     *            the name for this argument
     * @param value
     *            the value to add as an argument
     * @return this Command
     */
    public Command property(String key, Object value) {
        setProperty(key, value);
        return this;
    }

    /**
     * Adds the given argument to the Command with an argument name of "value"
     * 
     * @param value
     *            the value to add as an argument
     * @return this Command
     */
    public Command property(Serializable value) {
        setProperty("value", value);
        return this;
    }

    @Override
    public void setProperties(Map<String, Object> propertyMap) {
        properties.clear();
        properties = propertyMap;
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    /**
     * Convenience method for setPropertyMap
     * 
     * @param arguments
     *            the arguments/properties map
     */
    public void setArguments(Map<String, Object> arguments) {
        setProperties(arguments);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }


    @Override
    public boolean isToRecord() {
        return toRecord;
    }

    @Override
    public void setToRecord(boolean toRecord) {
        this.toRecord = toRecord;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {

        return "Command [action=" + action + ", id=" + id + ", properties=" + DCMSerialize.serialize(getProperties())
                + "]";
    }

}
