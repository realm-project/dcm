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

package net.realmproject.dcm.util;


import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;


/**
 * Utility functions for serializing, deserializing and performing message
 * format conversions.
 * 
 * @author NAS
 *
 */
public class DCMJsonSerialize {

    private final static ObjectMapper SERIALIZE_WITHOUT_CLASSES = new ObjectMapper();

    static {
        SERIALIZE_WITHOUT_CLASSES.enable(SerializationConfig.Feature.INDENT_OUTPUT);
    }

    private final static ObjectMapper SERIALIZE_WITH_CLASSES = new ObjectMapper();

    static {
        SERIALIZE_WITH_CLASSES.enable(SerializationConfig.Feature.INDENT_OUTPUT);
        SERIALIZE_WITH_CLASSES.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }

    private final static ObjectMapper DESERIALIZE = new ObjectMapper();

    static {
        DESERIALIZE.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Serializable> structToMap(Object o) {
        Object o2 = deserialize(serialize(o));
        if (!(o2 instanceof Map)) o = Collections.singletonMap("value", o);
        return (Map<String, Serializable>) deserialize(serialize(o));
    }

    @SuppressWarnings("unchecked")
    public static <T> T convertObject(Object object, Class<T> clazz) {
        // If this object is of class 'clazz', then just return it
        if (clazz.isAssignableFrom(object.getClass())) { return (T) object; }
        // Otherwise, use json serialization to try and coerce the data into the
        // desired class
        String asString = serialize(object);
        return deserialize(asString, clazz);
    }

    public static Object deserialize(String json) {
        try {
            return DESERIALIZE.reader(Object.class).readValue(json);
        }
        catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String json, Class<T> clazz) {
        try {
            return (T) DESERIALIZE.reader(clazz).readValue(json);
        }
        catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public static String serializeWithClassInfo(Object o) {
        try {
            return SERIALIZE_WITH_CLASSES.writeValueAsString(o);
        }
        catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public static String serialize(Object o) {

        try {
            return SERIALIZE_WITHOUT_CLASSES.writeValueAsString(o);
        }
        catch (IOException e) {
            throw new SerializationException(e);
        }
    }

}
