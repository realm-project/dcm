package net.realmproject.dcm.parcel.impl.flow.transform;

import java.util.function.Function;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.flow.transform.ParcelTransformLink;
import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.parcel.core.metadata.ParcelNodeType;
import net.realmproject.dcm.parcel.impl.flow.link.IParcelLink;

@ParcelMetadata (name="Transform", type=ParcelNodeType.TRANSFORM)
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
