package net.realmproject.dcm.stock.examples.webserver;

import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.parcel.core.metadata.ParcelNodeType;
import net.realmproject.dcm.parcel.impl.flow.transform.IParcelTransformLink;

@ParcelMetadata(name="File Server Link", type=ParcelNodeType.TRANSFORM)
public class FileServerLink extends IParcelTransformLink {

	private FileServer server;
	
	public FileServerLink() {
		server = new FileServer();
		setTransform(server);
	}

	public String getBasepath() {
		return server.getBasepath();
	}

	public void setBasepath(String basepath) {
		server.setBasepath(basepath);
	}
	
	
	
}
