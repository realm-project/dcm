package net.realmproject.dcm.stock.examples.webserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.link.ParcelLink;
import net.realmproject.dcm.parcel.core.service.ParcelService;
import net.realmproject.dcm.parcel.impl.service.IParcelService;
import net.realmproject.dcm.parcel.impl.transform.IParcelTransformLink;

public class WebServer {

	public static void main(String[] args) throws Exception {
		
		ParcelService<WebContext, WebContext> service = new IParcelService<>();

		
		ParcelLink hello = new IParcelTransformLink(p -> {
			WebContext request = (WebContext) p.getPayload();
			
			try {
				HttpServletResponse r = request.response;
				r.setContentType("text/html;charset=utf-8");
		        r.setStatus(HttpServletResponse.SC_OK);
		        r.getWriter().println("<h1>Hello</h1>");
				//request.response.
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return p;
		});
		
		ParcelLink world = new IParcelTransformLink(p -> {
			WebContext request = (WebContext) p.getPayload();
			
			try {
				HttpServletResponse r = request.response;
				r.setContentType("text/html;charset=utf-8");
		        r.setStatus(HttpServletResponse.SC_OK);
		        r.getWriter().println("<h1>World</h1>");
				//request.response.
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return p;
		});
		
		
		Map<String, ParcelReceiver> brancher2Map = new HashMap<>();
		brancher2Map.put("hello", hello);
		brancher2Map.put("world", world);
		IURLPathBrancher brancher2 = new IURLPathBrancher(brancher2Map);
		
		
		//just to demonstrate passing path traversal state from one handler to another
		Map<String, ParcelReceiver> brancher1Map = new HashMap<>();
		brancher1Map.put("words", brancher2);
		IURLPathBrancher brancher1 = new IURLPathBrancher(brancher1Map);
		
		//link nodes
		service.link(brancher1);
		hello.link(service);
		world.link(service);
		
		
		
		ParcelServiceServlet servlet = new ParcelServiceServlet(service);
				
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
