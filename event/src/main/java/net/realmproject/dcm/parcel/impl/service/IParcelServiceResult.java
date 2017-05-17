package net.realmproject.dcm.parcel.impl.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class IParcelServiceResult<T> implements Future<T> {

	private BlockingQueue<T> resultQueue;
	T result;
	boolean completed = false;
	
	public IParcelServiceResult(BlockingQueue<T> resultQueue) {
		this.resultQueue = resultQueue;
	}
	
	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean isDone() {
		return completed;
	}

	@Override
	public synchronized T get() throws InterruptedException, ExecutionException {
		if (!completed) {
			result = resultQueue.take();
			completed = true;
		}
		return result;
	}

	@Override
	public synchronized T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		if (!completed) {
			result = resultQueue.poll(timeout, unit);
			if (result == null) {
				//timed out
				return null;
			}
			completed = true;
		}
		return result;
	}

}
