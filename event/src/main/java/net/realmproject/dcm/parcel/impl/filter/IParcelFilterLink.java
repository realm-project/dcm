package net.realmproject.dcm.parcel.impl.filter;

import java.util.function.Predicate;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.filter.ParcelFilterLink;
import net.realmproject.dcm.parcel.core.hub.ParcelHub;
import net.realmproject.dcm.parcel.impl.link.IParcelLink;

public class IParcelFilterLink extends IParcelLink implements ParcelFilterLink {

    private Predicate<Parcel<?>> filter = null;
	

    public IParcelFilterLink() {}
    
    public IParcelFilterLink(ParcelHub from) {
    	super(from);
    }

    @Override
    public Predicate<Parcel<?>> getFilter() {
        return filter;
    }

    @Override
    public void setFilter(Predicate<Parcel<?>> filter) {
        this.filter = filter;
    }
	
}
