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
import java.util.UUID;

import net.realmproject.dcm.features.Identity;


public class Command implements Serializable, Identity {

    
    private String id;

    public Command() {
        id = "Command-" + UUID.randomUUID().toString();
    }

    public Command(String action) {
        this.action = action;
    }

    public Command(String action, Map<String, Serializable> args) {
        this.action = action;
        this.arguments.putAll(args);
    }

    /**
     * The name of the action or command to invoke on the {@link CommandDevice}
     */
    public String action;

    /**
     * The arguments to invoke the command with
     */
    public Map<String, Object> arguments = new HashMap<>();

    /**
     * Indicates if this command should be recorded (if such facilities are
     * available)
     */
    public boolean record;

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
        arguments.put(key, value);
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
        arguments.put("value", value);
        return this;
    }

    @Override
    public String getId() {
        return id;
    }

}
