package net.realmproject.dcm.stock.examples.webserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import net.realmproject.dcm.parcel.core.Logging;
import net.realmproject.dcm.parcel.core.ParcelNode;
import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.parcel.core.metadata.ParcelNodeType;
import net.realmproject.dcm.parcel.core.service.ParcelService;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;
import net.realmproject.dcm.util.DCMSettings;
import net.realmproject.dcm.util.DCMThreadPool;
import net.realmproject.dcm.util.DCMUtil;

@ParcelMetadata(name="Jetty Server", type=ParcelNodeType.ADAPTER)
public class JettyServer extends IParcelNode implements Logging{

	private ParcelService<WebContext, WebContext> service;
	private Server server;
	
	public JettyServer() {
		server = new Server(new InetSocketAddress("localhost", 8080));
		server.setHandler(new AbstractHandler() {
			
			@Override
			public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
				try {
					service.call(new WebContext(request, response)).get();
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				baseRequest.setHandled(true);
			}
		});
		
		DCMThreadPool.getScheduledPool().schedule(this::start, DCMSettings.CREATION_DELAY, TimeUnit.SECONDS);
		
	}

	private void start() {
		try {
			server.start();
		} catch (Exception e) {
			getLog().error("Failed to start Jetty Server", e);
		}
	}
	
	public ParcelService<WebContext, WebContext> getService() {
		return service;
	}

	public void setService(ParcelService<WebContext, WebContext> service) {
		this.service = service;
	}

	

	
}
