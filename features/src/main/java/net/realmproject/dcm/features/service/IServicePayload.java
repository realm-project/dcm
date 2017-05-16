package net.realmproject.dcm.features.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class IServicePayload<T> implements ServicePayload<T> {

	Object value;
	BlockingQueue<T> queue = new LinkedBlockingDeque<>();
	T result = null;
	boolean completed = false;
	
	
	public IServicePayload(Object input) {
		this.value = input;
	}
	

	/* (non-Javadoc)
	 * @see net.realmproject.dcm.features.service.ServicePayload#getResult()
	 */
	@Override
	public synchronized T getResult() throws InterruptedException {
		if (completed == false) {
			result = queue.take();
			completed = true;
		}
		return result;
	}
	
	public T getResult(long timeout, TimeUnit unit) throws InterruptedException {
		if (completed == false) {
			T value = queue.poll(timeout, unit);
			if (value == null) {
				return null; //timed out
			}
			result = value;
			completed = true;
		}
		return result;
	}


	@Override
	public void setResult(T result) {
		queue.offer(result);
	}
	

	@Override
	public Object getValue() {
		return value;
	}
	

	@Override
	public void setValue(Object value) {
		this.value = value;
	}


	@Override
	public boolean isDone() {
		return completed;
	}



	
	
}
