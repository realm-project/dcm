package net.realmproject.dcm.parcel.impl.transform;

import java.util.function.Function;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.transform.ParcelTransformLink;
import net.realmproject.dcm.parcel.impl.link.IParcelLink;

public class IParcelTransformLink extends IParcelLink implements ParcelTransformLink {

	private Function<Parcel<?>, Parcel<?>> transform = null;

    
	public IParcelTransformLink() {}

	public IParcelTransformLink(Function<Parcel<?>, Parcel<?>> transform) {
		this.transform = transform;
	}
	

    @Override
    public Function<Parcel<?>, Parcel<?>> getTransform() {
        return transform;
    }

    @Override
    public void setTransform(Function<Parcel<?>, Parcel<?>> transform) {
        this.transform = transform;
    }


	
}
