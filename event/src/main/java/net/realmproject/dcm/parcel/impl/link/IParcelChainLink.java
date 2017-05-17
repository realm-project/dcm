package net.realmproject.dcm.parcel.impl.link;

import java.util.Arrays;
import java.util.List;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.link.Linkable;

public class IParcelChainLink extends IParcelLink {

	private Linkable first;
	private Linkable last;
	
	public IParcelChainLink(Linkable... links) {
		this(Arrays.asList(links));
	}
	
	public IParcelChainLink(List<Linkable> links) {
		
		first = links.get(0);
		
		Linkable previous = first;
		for (Linkable l : links) {
			if (l == first) { continue; }
			previous = previous.link(l);
		}
		last = previous;
		last.link(getReceiver());
		
	}
	
	
	@Override
	public void receive(Parcel<?> parcel) {
		first.receive(parcel);
	}
	
	public void setReceiver(ParcelReceiver receiver) {
		super.setReceiver(receiver);
		last.link(receiver);
	}
	

}
