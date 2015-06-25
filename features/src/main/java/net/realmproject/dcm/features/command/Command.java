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


import java.util.HashMap;
import java.util.Map;

import net.realmproject.dcm.event.identity.Identity;
import net.realmproject.dcm.features.Properties;
import net.realmproject.dcm.features.recording.Recordable;


public class Command implements Properties<Object>, Recordable, Identity {

    /**
     * The name of the action or command to invoke on the {@link CommandDevice}
     */
    private String action;

    private String id;
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

    public Command(String action, Map<String, ?> args) {
        this();
        this.action = action;
        this.getPropertyMap().putAll(args);
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
    public Command arg(String key, Object value) {
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
    public Command arg(Object value) {
        setProperty("value", value);
        return this;
    }

    @Override
    public void setPropertyMap(Map<String, Object> propertyMap) {
        properties.clear();
        properties = propertyMap;
    }

    @Override
    public Map<String, Object> getPropertyMap() {
        return properties;
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

}
