package net.realmproject.dcm.parcel.impl.flow.filter;

import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.parcel.core.metadata.ParcelNodeType;
import net.realmproject.dcm.parcel.impl.flow.filter.filters.JavaScriptFilter;

@ParcelMetadata (name="JavaScript Filter", type=ParcelNodeType.FILTER)
public class JavaScriptParcelFilterLink extends IParcelFilterLink {

	JavaScriptFilter jsfilter = new JavaScriptFilter("");
	
	public JavaScriptParcelFilterLink() {
		setFilter(jsfilter);
	}

	public String getScript() {
		return jsfilter.getScript();
	}

	public void setScript(String script) {
		jsfilter.setScript(script);
	}
	
	
	
}
