package net.realmproject.dcm.parcel.router;

import java.util.function.Consumer;

import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.receiver.ParcelReceiver;
import net.realmproject.dcm.util.DCMUtil;

public class IRoutingParcelConsumer implements ParcelReceiver, Routing {

	private String id = DCMUtil.generateId();
	private String nextHop = null;
	private Consumer<Parcel<?>> consumer;

	public IRoutingParcelConsumer(Consumer<Parcel<?>> consumer) {
		this.consumer = consumer;
	}
	
	public IRoutingParcelConsumer(String consumerId, Consumer<Parcel<?>> consumer) {
		this.nextHop = consumerId;
		this.consumer = consumer;
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void accept(Parcel<?> parcel) {
		consumer.accept(parcel);
	}

	@Override
	public RoutingTable getRoutes(String previousHop) {
		RoutingTable routes = new IRoutingTable();
		routes.addLocal(id);
		routes.addRoute(nextHop, id, 1);
		routes.markTime();
		return routes;
	}

}
