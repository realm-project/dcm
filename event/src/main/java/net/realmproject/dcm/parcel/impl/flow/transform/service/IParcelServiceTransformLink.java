package net.realmproject.dcm.parcel.impl.flow.transform.service;

import net.realmproject.dcm.parcel.core.service.ParcelService;
import net.realmproject.dcm.parcel.impl.flow.transform.IParcelTransformLink;

public class IParcelServiceTransformLink extends IParcelTransformLink {

	ParcelServiceTransform<?, ?> serviceTransform;
	
	public IParcelServiceTransformLink() {
		serviceTransform = new ParcelServiceTransform<>();
	}

	public ParcelService<?, ?> getService() {
		return serviceTransform.getService();
	}

	public void setService(ParcelService<?, ?> service) {
		serviceTransform.setService((ParcelService) service);
	}
	
	
	
	
	
}
