package net.realmproject.dcm.parcel.impl.transform;

import java.util.function.Function;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelLink;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.hub.ParcelHub;
import net.realmproject.dcm.parcel.core.transform.ParcelTransformLink;
import net.realmproject.dcm.parcel.impl.link.IParcelLink;
import net.realmproject.dcm.parcel.impl.node.IParcelNode;

public class IParcelTransformLink extends IParcelLink implements ParcelTransformLink {

	private Function<Parcel<?>, Parcel<?>> transform = null;

    public IParcelTransformLink(ParcelReceiver to) {
        super(to);
    }
    
    public IParcelTransformLink(ParcelHub from, ParcelReceiver to) {
    	super(from, to);
    }

    @Override
    public void send(Parcel<?> parcel) {
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
