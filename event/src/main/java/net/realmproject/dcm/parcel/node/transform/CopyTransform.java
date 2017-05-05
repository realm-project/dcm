package net.realmproject.dcm.parcel.node.transform;

import java.util.function.Function;

import net.realmproject.dcm.parcel.Parcel;

public class CopyTransform implements Function<Parcel<?>, Parcel<?>> {

	@Override
	public Parcel<?> apply(Parcel<?> t) {
		return t.deepCopy();
	}

}
