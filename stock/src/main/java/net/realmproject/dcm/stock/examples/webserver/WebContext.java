package net.realmproject.dcm.stock.examples.webserver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.realmproject.dcm.features.IPropreties;

public class WebContext extends IPropreties<Object> {

	public HttpServletRequest request;
	public HttpServletResponse response;
	
}
