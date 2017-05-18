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
    
    default <R extends T> R getProperty(Class<?> clazz, String key, R defaultValue) {
        return getProperty(clazz.getName()  + ":" + key, defaultValue);
    }

    default <R extends T> R getProperty(Class<?> clazz, String key) {
    	return getProperty(clazz.getName() + ":" + key);
    }
    

    default void setProperty(String key, T value) {
        getProperties().put(key, value);
    }
    
    default void setProperty(Class<?> clazz, String key, T value) {
    	setProperty(clazz.getName() + ":" + key, value);
    }

    void setProperties(Map<String, T> propertyMap);

    Map<String, T> getProperties();

}
