package net.realmproject.dcm.stock.examples.webserver;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.realmproject.dcm.parcel.core.service.ParcelService;



public class WebServer extends HttpServlet  {

	private ParcelService<WebRequest, WebRequest> service;
	
	public WebServer(ParcelService<WebRequest, WebRequest> service) {
		this.service = service;
	}
	
	@Override
	public void init() throws ServletException {
		
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebRequest webrequest = new WebRequest();
		webrequest.request = request;
		webrequest.response = response;

		try {
			service.call(webrequest).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
	}

}
