package net.realmproject.dcm.stock.examples.webserver;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import net.realmproject.dcm.parcel.core.link.ParcelLink;
import net.realmproject.dcm.parcel.core.service.ParcelService;
import net.realmproject.dcm.parcel.impl.service.IParcelService;
import net.realmproject.dcm.parcel.impl.transform.IParcelTransformLink;

public class Web {

	public static void main(String[] args) throws Exception {
		
		ParcelService<WebRequest, WebRequest> service = new IParcelService<>();
		ParcelLink pathinfo = new IParcelTransformLink(p -> {
			WebRequest request = (WebRequest) p.getPayload();
			
			try {
				HttpServletResponse r = request.response;
				r.setContentType("text/html;charset=utf-8");
		        r.setStatus(HttpServletResponse.SC_OK);
		        r.getWriter().println("<h1>Path</h1>\n" + request.request.getPathInfo());
				//request.response.
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return p;
		});
		
		service.link(pathinfo).link(service);
		
		
		
		WebServer servlet = new WebServer(service);
				
		Server server = new Server(new InetSocketAddress("localhost", 8080));
		
		
		server.setHandler(new AbstractHandler() {
			
			@Override
			public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
				servlet.service(request, response);
				baseRequest.setHandled(true);
			}
		});
		
		server.start();
		Thread.sleep(20000);
		server.stop();
		
	}
	
}
