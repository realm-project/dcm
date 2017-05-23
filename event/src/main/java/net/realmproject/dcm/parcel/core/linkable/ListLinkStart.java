package net.realmproject.dcm.parcel.core.linkable;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;
import net.realmproject.dcm.parcel.core.ParcelSender;
import net.realmproject.dcm.parcel.core.flow.filter.ParcelFilterLink;
import net.realmproject.dcm.parcel.core.flow.transform.ParcelTransformLink;
import net.realmproject.dcm.parcel.impl.flow.filter.IParcelFilterLink;
import net.realmproject.dcm.parcel.impl.flow.transform.IParcelTransformLink;

public interface ListLinkStart extends ParcelSender {

	default SingleLinkable link(SingleLinkable link) {
		link((ParcelReceiver) link);
		return link;
	}
	default ListLinkable link(ListLinkable link) {
		link((ParcelReceiver) link);
		return link;
	}
	default NamedLinkable link(NamedLinkable link) {
		link((ParcelReceiver) link);
		return link;
	}
	
	
	void link(ParcelReceiver receiver);
	void unlink(ParcelReceiver receiver);
	
	default void linkAll(List<ParcelReceiver> links) {
		for (ParcelReceiver l : links) {
			link(l);
		}
	}
	
	
	default void unlinkAll() {
		for (ParcelReceiver l : getLinks()) {
			unlink(l);
		}
	}
	
	default SingleLinkable filter(Predicate<Parcel<?>> filterer) {
		ParcelFilterLink node = new IParcelFilterLink(filterer);
		return link(node);
	}
	
	default SingleLinkable transform(Function<Parcel<?>, Parcel<?>> transformer) {
		ParcelTransformLink node = new IParcelTransformLink(transformer);
		return link(node);
	}
	
}
