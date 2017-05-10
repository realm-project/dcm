package net.realmproject.dcm.parcel.node.transform;

import java.util.function.Function;

import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.node.IParcelNode;
import net.realmproject.dcm.parcel.node.hub.ParcelHub;
import net.realmproject.dcm.parcel.node.link.IParcelLink;
import net.realmproject.dcm.parcel.node.link.ParcelLink;
import net.realmproject.dcm.parcel.node.receiver.ParcelReceiver;

public class IParcelTransformLink extends IParcelLink implements ParcelTransformLink {

	private Function<Parcel<?>, Parcel<?>> transform = null;

    public IParcelTransformLink(ParcelReceiver to) {
        super(to);
    }
    
    public IParcelTransformLink(ParcelHub from, ParcelReceiver to) {
    	super(from, to);
    }

    @Override
    public void receive(Parcel<?> parcel) {
        if (!parcel.visit(getId())) { return; } //cycle detection
        parcel = transform(parcel);
        if (to != null) {
        	to.receive(parcel);
        }
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
