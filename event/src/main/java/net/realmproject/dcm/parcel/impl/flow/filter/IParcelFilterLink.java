package net.realmproject.dcm.parcel.impl.flow.filter;

import java.util.function.Predicate;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.flow.filter.ParcelFilterLink;
import net.realmproject.dcm.parcel.impl.flow.link.IParcelLink;

public class IParcelFilterLink extends IParcelLink implements ParcelFilterLink {

    private Predicate<Parcel<?>> filter = null;
	

    public IParcelFilterLink() {}
    
    public IParcelFilterLink(Predicate<Parcel<?>> filter) {
    	this.filter = filter;
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
