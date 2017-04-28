package net.realmproject.dcm.stock.event;

import java.util.function.Predicate;

import net.realmproject.dcm.parcel.Parcel;

public class RateLimitFilter implements Predicate<Parcel>{

	private long lastEventSent = 0l;
	private long interval = 1000l; 
	
	public RateLimitFilter(long interval) {
		this.interval = interval;
	}
	
	@Override
	public boolean test(Parcel t) {
		long current = System.currentTimeMillis();
		if (current - lastEventSent < interval) { return false; }
		lastEventSent = current;
		return true;
	}

}
