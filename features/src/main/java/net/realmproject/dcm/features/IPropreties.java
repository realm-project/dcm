package net.realmproject.dcm.features;

import java.util.HashMap;
import java.util.Map;

public class IPropreties<T> implements Properties<T> {

	private Map<String, T> properties = new HashMap<>();
	
	@Override
	public void setProperties(Map<String, T> propertyMap) {
		this.properties = propertyMap;
	}

	@Override
	public Map<String, T> getProperties() {
		return properties;
	}

}
