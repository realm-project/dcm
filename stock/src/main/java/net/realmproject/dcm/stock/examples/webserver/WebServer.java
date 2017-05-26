package net.realmproject.dcm.stock.examples.webserver;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import net.realmproject.dcm.parcel.core.flow.link.ParcelLink;
import net.realmproject.dcm.parcel.core.service.ParcelService;
import net.realmproject.dcm.parcel.impl.flow.transform.IParcelTransformLink;
import net.realmproject.dcm.parcel.impl.service.IParcelService;

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
		
		
		
		JettyService service = new JettyService();

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
		
		URL url = WebServer.class.getResource("contents");
		File f = new File(url.toURI());
		FileServerLink fileserver = new FileServerLink();
		fileserver.setBasepath(f.toString());
		
		
		IURLPathBrancher brancher2 = new IURLPathBrancher();

		
		
		//just to demonstrate passing path traversal state from one handler to another
		IURLPathBrancher brancher1 = new IURLPathBrancher();
		
		
		//link nodes
//		service.link(brancher1);
//		brancher1.link("files", fileserver).link(service);
//		brancher1.link("words", brancher2);
		service.link(fileserver).link(service);
		
//		brancher2.link("hello",  hello);
//		brancher2.link("world", world);
//		hello.link(service);
//		world.link(service);
		

		
	}
	
}
