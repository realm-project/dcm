package net.realmproject.dcm.parcel.core.service;

import java.util.concurrent.Future;

import net.realmproject.dcm.parcel.core.linkable.Linkable;

public interface ParcelService<F, T> extends Linkable {
	
	Future<T> call(F input);

}