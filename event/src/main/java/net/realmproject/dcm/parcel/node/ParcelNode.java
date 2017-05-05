package net.realmproject.dcm.parcel.node;


import java.util.function.Function;
import java.util.function.Predicate;

import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.node.identity.Identity;


/**
 * Base interface for parcel node graph nodes.
 * 
 * @author NAS
 *
 */

public interface ParcelNode extends Identity {

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

    // Transform Function
    Function<Parcel<?>, Parcel<?>> getTransform();

    void setTransform(Function<Parcel<?>, Parcel<?>> transform);

    /**
     * Method for transforming {@link Parcel}s using the specified
     * transformation function
     * 
     * @param parcel
     *            the parcel to transform
     * 
     * @return the (optionally) modified parcel
     * 
     */
    default Parcel<?> transform(Parcel<?> parcel) {
        if (getTransform() == null) { return parcel; }
        return getTransform().apply(parcel);
    }

}