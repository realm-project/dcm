package net.realmproject.dcm.parcel.impl.filter.filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import net.realmproject.dcm.parcel.core.Parcel;

public class ParcelIDFilter implements Predicate<Parcel<?>> {

	List<String> validIDs = new ArrayList<>();
	
	public ParcelIDFilter(String... ids) {
		validIDs.addAll(Arrays.asList(ids));
	}
	
	public ParcelIDFilter(Collection<String> ids) {
		validIDs.addAll(ids);
	}
	
	@Override
	public boolean test(Parcel<?> t) {
		return validIDs.contains(t.getId());
	}

}
