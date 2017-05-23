package net.realmproject.dcm.stock.examples.webserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.realmproject.dcm.features.IPropreties;

public class WebContext extends IPropreties<Object> {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private List<String> path;
	private List<String> traversed;
	
	public WebContext(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		path = new ArrayList<>(Arrays.asList(request.getPathInfo().substring(1).split("/")));
		traversed = new ArrayList<>();
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}
	
	public List<String> getRemainingPath() {
		return path;
	}
	
	public List<String> getTraversedPath() {
		return traversed;
	}
	
	public String traversePath() {
		String p = path.remove(0);
		traversed.add(p);
		return p;
	}
	
	
	
}
