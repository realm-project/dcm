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

    default <R extends T> R getProperty(String key, R defaultValue) {
        if (!getProperties().containsKey(key)) { return defaultValue; }
        return (R) getProperties().get(key);
    }

    default <R extends T> R getProperty(String key) {
        return getProperty(key, null);
    }

    default void setProperty(String key, T value) {

        getProperties().put(key, value);
    }

    void setProperties(Map<String, T> propertyMap);

    Map<String, T> getProperties();

}
