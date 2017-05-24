package net.realmproject.dcm.parcel.impl.flow.transform.javascript;

import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.parcel.core.metadata.ParcelNodeType;
import net.realmproject.dcm.parcel.impl.flow.transform.IParcelTransformLink;

@ParcelMetadata (name="JavaScript Transform", type=ParcelNodeType.TRANSFORM)
public class JavaScriptParcelTransformLink extends IParcelTransformLink {

	JavaScriptParcelTransform jstransform = new JavaScriptParcelTransform("");
	
	public JavaScriptParcelTransformLink() {
		setTransform(jstransform);
	}

	public String getScript() {
		return jstransform.getScript();
	}

	public void setScript(String script) {
		jstransform.setScript(script);
	}
	
	
	
}
