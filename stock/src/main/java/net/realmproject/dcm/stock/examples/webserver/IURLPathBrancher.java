package net.realmproject.dcm.stock.examples.webserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.impl.branch.AbstractParcelBranch;

public class IURLPathBrancher extends AbstractParcelBranch {

	public IURLPathBrancher(Map<String, ParcelReceiver> receivers) {
		super(receivers);
	}

	protected String getBranch(Parcel<?> p) {
		WebContext cx = (WebContext) p.getPayload();
		return getRemainingPath(cx).remove(0);
	}
	
	private List<String> getRemainingPath(WebContext cx) {
		String key = "remaining-path";
		Class<?> clazz = IURLPathBrancher.class;
		
		List<String> path = cx.getProperty(clazz, key);
		if (path == null) {
			path = new ArrayList<>(Arrays.asList(cx.getRequest().getPathInfo().substring(1).split("/")));
			cx.setProperty(clazz, key, path);
		}
		return path;
	}
	

	

}
