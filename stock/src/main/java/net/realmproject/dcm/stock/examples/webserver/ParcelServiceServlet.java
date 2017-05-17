package net.realmproject.dcm.stock.examples.webserver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.realmproject.dcm.parcel.core.service.ParcelService;


public class ParcelServiceServlet extends HttpServlet  {

	private ParcelService<WebContext, WebContext> service;
	
	public ParcelServiceServlet(ParcelService<WebContext, WebContext> service) {
		this.service = service;
	}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service.call(new WebContext(request, response));
	}

}
