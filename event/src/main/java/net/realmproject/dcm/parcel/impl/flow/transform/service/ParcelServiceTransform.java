package net.realmproject.dcm.parcel.impl.flow.transform.service;

import java.util.function.Function;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.service.ParcelService;

public class ParcelServiceTransform<F, T> implements Function<Parcel<?>, Parcel<?>>{

	private ParcelService<F, T> service;
	
	public ParcelServiceTransform() {}
	
	@Override
	public Parcel<?> apply(Parcel<?> t) {
		Parcel<Object> op = (Parcel<Object>) t; 
		op.setPayload((service.call((F)t.getPayload())));
		return t;
	}

	public ParcelService<F, T> getService() {
		return service;
	}

	public void setService(ParcelService<F, T> service) {
		this.service = service;
	}

	
	
	
	
}
