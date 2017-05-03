package net.realmproject.dcm.parcel;


import java.util.function.Function;
import java.util.function.Predicate;

import net.realmproject.dcm.util.DCMUtil;


public class IParcelNode implements ParcelNode {

	private String id = DCMUtil.generateId();
    private Predicate<Parcel<?>> filter = null;
    private Function<Parcel<?>, Parcel<?>> transform = null;

    public IParcelNode(String id) {
        super();
        this.id = id;
    }
    
    public IParcelNode() {
        super();
    }

    @Override
    public Predicate<Parcel<?>> getFilter() {
        return filter;
    }

    @Override
    public void setFilter(Predicate<Parcel<?>> filter) {
        this.filter = filter;
    }

    @Override
    public Function<Parcel<?>, Parcel<?>> getTransform() {
        return transform;
    }

    @Override
    public void setTransform(Function<Parcel<?>, Parcel<?>> transform) {
        this.transform = transform;
    }

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

}