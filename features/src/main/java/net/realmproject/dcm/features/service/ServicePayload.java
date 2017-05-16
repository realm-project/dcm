package net.realmproject.dcm.features.service;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface ServicePayload<T> extends Future<T>{

	T getResult() throws InterruptedException;
	T getResult(long timeout, TimeUnit unit) throws InterruptedException;

	void setResult(T result);

	Object getValue();

	void setValue(Object value);
	
	
	
	@Override
	default boolean cancel(boolean mayInterrupt) {
		return false;
	}
	
	@Override
	default T get() throws InterruptedException, ExecutionException {
		return getResult();
	}
	
	@Override
	default T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return getResult(timeout, unit);
	}
	
	@Override
	default boolean isCancelled() {
		return false;
	}

}