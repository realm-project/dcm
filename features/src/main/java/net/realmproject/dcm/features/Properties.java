package net.realmproject.dcm.features;


import java.util.Map;


/**
 * Generic interface for object containing a property map. The getPropertyMap
 * and setPropertyMap methods should get/set a direct reference to the backing
 * property map.
 * 
 * @author NAS
 *
 * @param <T>
 *            The type of property values in this property map
 */
public interface Properties<T> {

    default T getProperty(String key, T defaultValue) {
        if (!getPropertyMap().containsKey(key)) { return defaultValue; }
        return getPropertyMap().get(key);
    }

    default T getProperty(String key) {
        return getProperty(key, null);
    }

    default void setProperty(String key, T value) {
        getPropertyMap().put(key, value);
    }

    void setPropertyMap(Map<String, T> propertyMap);

    Map<String, T> getPropertyMap();

}
