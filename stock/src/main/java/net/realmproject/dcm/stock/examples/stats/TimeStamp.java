package net.realmproject.dcm.stock.examples.stats;

import java.util.Date;
import java.util.function.Function;

import net.realmproject.dcm.parcel.core.Parcel;

public class TimeStamp implements Function<Parcel<?>, Parcel<?>> {

	@Override
	public Parcel<?> apply(Parcel<?> t) {
		StringBuilder sb = (StringBuilder) t.getPayload();
		sb.append(new Date());
		sb.append("\n");
		return t;
	}

}
