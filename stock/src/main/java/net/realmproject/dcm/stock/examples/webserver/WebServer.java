package net.realmproject.dcm.stock.examples.webserver;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import net.realmproject.dcm.parcel.core.link.ParcelLink;
import net.realmproject.dcm.parcel.core.service.ParcelService;
import net.realmproject.dcm.parcel.impl.service.IParcelService;
import net.realmproject.dcm.parcel.impl.transform.IParcelTransformLink;

public class WebServer {

	public static void respond(WebContext cx, String word) {
		try {
			HttpServletResponse r = cx.getResponse();
			r.setContentType("text/html;charset=utf-8");
	        r.setStatus(HttpServletResponse.SC_OK);
	        r.getWriter().println("<h1>" + word + "</h1>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		
		
		ParcelService<WebContext, WebContext> service = new IParcelService<>();

		//Respond with "Hello"
		ParcelLink hello = new IParcelTransformLink(p -> {
			WebContext cx = (WebContext) p.getPayload();
			respond(cx, "Hello");
			return p;
		});
		
		//Respond with "World"
		ParcelLink world = new IParcelTransformLink(p -> {
			WebContext cx = (WebContext) p.getPayload();
			respond(cx, "World");			
			return p;
		});
		
		
		IURLPathBrancher brancher2 = new IURLPathBrancher();

		
		
		//just to demonstrate passing path traversal state from one handler to another
		IURLPathBrancher brancher1 = new IURLPathBrancher();
		
		
		//link nodes
		service.link(brancher1);
		brancher1.link("words", brancher2);
		brancher2.link("hello",  hello);
		brancher2.link("world", world);
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
