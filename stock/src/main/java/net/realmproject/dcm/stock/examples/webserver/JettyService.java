package net.realmproject.dcm.stock.examples.webserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import net.realmproject.dcm.parcel.core.Logging;
import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.parcel.core.metadata.ParcelNodeType;
import net.realmproject.dcm.parcel.impl.service.IParcelService;

@ParcelMetadata(name="Jetty Server", type=ParcelNodeType.ADAPTER)
public class JettyService extends IParcelService<WebContext, WebContext> implements Logging {

	private Server server;
	
	private String hostname = "localhost";
	private int port = 8080;
	
	public JettyService() {
		
		createServer();
		start();
		
	}

	private void createServer() {
		server = new Server(new InetSocketAddress(hostname, port));
		server.setHandler(new AbstractHandler() {
			
			@Override
			public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
				try {
					call(new WebContext(request, response)).get();
				} catch (InterruptedException | ExecutionException e) {
					getLog().error("Failed to process request", e);
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				} finally {
					baseRequest.setHandled(true);	
				}
				
			}
		});
	}
	
	private synchronized void start() {
		try {
			if (server.isStarted()) { return; } 
			server.start();
		} catch (Exception e) {
			getLog().error("Failed to start Jetty Server", e);
		}
	}
	
	private synchronized void stop() {
		try {
			if (server.isStopped()) { return; }
			server.stop();
		} catch (Exception e) {
			getLog().error("Failed to stop Jetty Server", e);
		}
	}
	
	private synchronized void restart() {
		stop();
		createServer();
		start();
	}
	

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
		restart();
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
		restart();
		
	}

	
	
	

	
}
