package net.realmproject.dcm.parcel.impl.flow.routing;

import java.util.function.Consumer;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.flow.routing.Routing;
import net.realmproject.dcm.parcel.core.flow.routing.RoutingTable;
import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.parcel.core.metadata.ParcelNodeType;
import net.realmproject.dcm.parcel.impl.flow.routing.routingtable.IRoute;
import net.realmproject.dcm.parcel.impl.flow.routing.routingtable.IRoutingTable;
import net.realmproject.dcm.util.DCMUtil;

@ParcelMetadata (name="Routing Consumer", type=ParcelNodeType.ENDPOINT)
public class IRoutingParcelConsumer implements ParcelReceiver, Routing {

	private String id = DCMUtil.generateId();
	private Consumer<Parcel<?>> consumer;

	public IRoutingParcelConsumer(Consumer<Parcel<?>> consumer) {
		this.consumer = consumer;
	}
	
	public IRoutingParcelConsumer(String consumerId, Consumer<Parcel<?>> consumer) {
		this.id = consumerId;
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
	public void receive(Parcel<?> parcel) {
		consumer.accept(parcel);
	}

	@Override
	public RoutingTable getRoutes() {
		RoutingTable routes = new IRoutingTable(this);
		return routes;
	}

}
