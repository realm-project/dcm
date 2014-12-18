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


import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.AbstractTransformer;


/**
 * Utility functions for serializing, deserializing and performing message
 * format conversions.
 * 
 * @author NAS
 *
 */
public class DCMSerialize {

    public static Map<String, Serializable> structToMap(Object o) {
        Object o2 = deserialize(serialize(o));
        if (!(o2 instanceof Map)) o = Collections.singletonMap("value", o);
        return (Map<String, Serializable>) deserialize(serialize(o));
    }

    public static <T> T convertMessage(Object publication, Class<T> clazz) {
        String asString = serialize(publication);
        return deserialize(asString, clazz);
    }

    public static Object deserialize(String json) {
        return new JSONDeserializer<Object>().deserialize(json);
    }

    public static <T> T deserialize(String json, Class<T> clazz) {
        return new JSONDeserializer<T>().deserialize(json, clazz);
    }

    public static String serialize(Object o) {
        return new JSONSerializer().transform(new ExcludeTransformer(), void.class).exclude("*.class")
                .prettyPrint(true).deepSerialize(o);
    }

}

class ExcludeTransformer extends AbstractTransformer {

    @Override
    public Boolean isInline() {
        return true;
    }

    @Override
    public void transform(Object object) {
        // Do nothing, null objects are not serialized.
        return;
    }
}
