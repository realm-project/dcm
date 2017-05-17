package net.realmproject.dcm.stock.examples.webserver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.realmproject.dcm.features.IPropreties;

public class WebContext extends IPropreties<Object> {

	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public WebContext(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}
	
	
	
}
