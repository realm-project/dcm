package net.realmproject.dcm.features.service;

import java.util.concurrent.Future;

public interface ParcelService<F, T> {

	Future<T> call(F input);

}