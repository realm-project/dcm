package net.realmproject.dcm.parcel.impl.link;

import java.util.Arrays;
import java.util.List;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelLink;
import net.realmproject.dcm.parcel.impl.receiver.IParcelConsumer;

public class IParcelChainLink extends IParcelLink {

	private ParcelLink first;
	
	public IParcelChainLink(ParcelLink... links) {
		this(Arrays.asList(links));
	}
	
	public IParcelChainLink(List<ParcelLink> links) {
		
		first = links.get(0);
		
		ParcelLink previous = first;
		for (ParcelLink l : links) {
			if (l == first) { continue; }
			previous = previous.link(l);
		}
		previous.link(new IParcelConsumer(this::send));
	}
	
	
	@Override
	public void receive(Parcel<?> parcel) {
		first.receive(parcel);
	}
	


}
