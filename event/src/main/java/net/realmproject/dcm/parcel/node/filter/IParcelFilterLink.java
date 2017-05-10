package net.realmproject.dcm.parcel.node.filter;

import java.util.function.Predicate;

import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.node.hub.ParcelHub;
import net.realmproject.dcm.parcel.node.link.IParcelLink;
import net.realmproject.dcm.parcel.node.receiver.ParcelReceiver;

public class IParcelFilterLink extends IParcelLink implements ParcelFilterLink {

    private Predicate<Parcel<?>> filter = null;
	

    public IParcelFilterLink(ParcelReceiver to) {
        super(to);
    }
    
    public IParcelFilterLink(ParcelHub from, ParcelReceiver to) {
    	super(from, to);
    }

    @Override
    public void receive(Parcel<?> parcel) {
        if (!filter(parcel)) { return; }
        if (!parcel.visit(getId())) { return; } //cycle detection
        if (to != null) {
        	to.receive(parcel);
        }
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
