package net.realmproject.dcm.parcel.core.service;

import java.util.concurrent.Future;

import net.realmproject.dcm.parcel.core.linkable.SingleLinkable;

public interface ParcelService<F, T> extends SingleLinkable {
	
	Future<T> call(F input);

}