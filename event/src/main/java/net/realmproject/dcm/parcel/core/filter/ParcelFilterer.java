package net.realmproject.dcm.parcel.core.filter;

import java.util.function.Predicate;

import net.realmproject.dcm.parcel.core.Parcel;

public interface ParcelFilterer {

    // Filter Predicate
    Predicate<Parcel<?>> getFilter();

    void setFilter(Predicate<Parcel<?>> filter);

    /**
     * Method for filtering {@link Parcel}s using the specified filter
     * 
     * @param parcel
     *            the parcel to filter against
     * 
     * @return true if the filter should pass the parcel, false if it should
     *         block it
     * 
     */
    default boolean filter(Parcel<?> parcel) {
        if (getFilter() == null) { return true; }
        if (!getFilter().test(parcel)) { return false; }
        return true;
    }
	
}
