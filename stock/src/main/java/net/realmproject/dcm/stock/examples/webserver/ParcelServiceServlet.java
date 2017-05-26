package net.realmproject.dcm.stock.examples.webserver;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.realmproject.dcm.parcel.core.Logging;
import net.realmproject.dcm.parcel.core.service.ParcelService;


public class ParcelServiceServlet extends HttpServlet implements Logging {

	private ParcelService<WebContext, WebContext> service;
	
	public ParcelServiceServlet() {}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			service.call(new WebContext(request, response)).get();
		} catch (InterruptedException | ExecutionException e) {
			getLog().error("Failed to handle request " + request.getRequestURL(), e);
		}
	}

	public ParcelService<WebContext, WebContext> getService() {
		return service;
	}

	public void setService(ParcelService<WebContext, WebContext> service) {
		this.service = service;
	}
	
	

}
