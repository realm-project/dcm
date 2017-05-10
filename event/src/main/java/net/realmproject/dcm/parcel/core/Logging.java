package net.realmproject.dcm.parcel.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public interface Logging {

	default Log getLog() {
		return LogFactory.getLog(getClass());
	}
	
}
