package net.realmproject.dcm.parcel.impl.flow.misc;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.parcel.core.metadata.ParcelNodeType;
import net.realmproject.dcm.parcel.impl.parcel.IParcel;
import net.realmproject.dcm.parcel.impl.sender.IParcelSender;
import net.realmproject.dcm.util.DCMSettings;
import net.realmproject.dcm.util.DCMThreadPool;

@ParcelMetadata (name="Beacon", type=ParcelNodeType.OTHER)
public class IParcelBeacon extends IParcelSender  {

	private Supplier<Object> supplier = () -> "Beacon";
	
	public IParcelBeacon() {
		this(1000);
	}
	
	public IParcelBeacon(long interval) {
		DCMThreadPool.getScheduledPool().scheduleAtFixedRate(() -> {
			send(new IParcel<>().payload(supplier.get()));
		}, DCMSettings.STARTUP_DELAY*1000, interval, TimeUnit.MILLISECONDS);
	}

	public Supplier<Object> getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier<Object> supplier) {
		this.supplier = supplier;
	}
	
	
	

}
