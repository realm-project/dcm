package net.realmproject.dcm.parcel.impl.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import net.realmproject.dcm.parcel.core.Parcel;

public class NameFilter implements Predicate<Parcel<?>> {

	List<String> validNames = new ArrayList<>();
	
	public NameFilter(String... names) {
		validNames.addAll(Arrays.asList(names));
	}
	
	public NameFilter(Collection<String> names) {
		validNames.addAll(names);
	}
	
	@Override
	public boolean test(Parcel<?> t) {
		return validNames.contains(t.getName());
	}

}
