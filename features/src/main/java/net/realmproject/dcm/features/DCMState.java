package net.realmproject.dcm.features;


import java.util.Map;


public interface DCMState {

    default <T> T getDCMState(Class<?> owner, String key, T defaultValue) {
        String fullKey = owner.getCanonicalName() + ":" + key;
        if (!getDCMStateMap().containsKey(fullKey)) {
            setDCMState(owner, key, defaultValue);
        }
        return (T) getDCMStateMap().get(fullKey);
    }

    default <T> T getDCMState(Class<?> owner, String key) {
        return getDCMState(owner, key, null);
    }

    default <T> void setDCMState(Class<?> owner, String key, T value) {
        String fullKey = owner.getCanonicalName() + ":" + key;
        getDCMStateMap().put(fullKey, value);
    }

    Map<String, Object> getDCMStateMap();

}
