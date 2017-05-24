package net.realmproject.dcm.parcel.impl.flow.link;

import java.util.Arrays;
import java.util.List;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.linkable.SingleLinkable;
import net.realmproject.dcm.parcel.core.metadata.ParcelMetadata;
import net.realmproject.dcm.parcel.core.metadata.ParcelNodeType;

@ParcelMetadata (name="Compound Link", type=ParcelNodeType.OTHER)
public class ICompoundParcelLink extends IParcelLink {

	private SingleLinkable first;
	private SingleLinkable last;
	
	public ICompoundParcelLink(SingleLinkable... links) {
		this(Arrays.asList(links));
	}
	
	public ICompoundParcelLink(List<SingleLinkable> links) {
		
		first = links.get(0);
		
		SingleLinkable previous = first;
		for (SingleLinkable l : links) {
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
