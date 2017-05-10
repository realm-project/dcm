package net.realmproject.dcm.parcel.node.transform;

import java.util.function.Function;

import net.realmproject.dcm.parcel.Parcel;

public interface ParcelTransformer {

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
