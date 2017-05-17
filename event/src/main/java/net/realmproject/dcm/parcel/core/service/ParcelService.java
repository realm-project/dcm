package net.realmproject.dcm.parcel.core.service;

import java.util.concurrent.Future;

public interface ParcelService<F, T> {

	Future<T> call(F input);

}