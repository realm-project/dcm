package net.realmproject.dcm.stock.examples.stats;

import java.util.function.Function;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.link.ParcelLink;
import net.realmproject.dcm.parcel.core.transform.ParcelTransformLink;
import net.realmproject.dcm.parcel.impl.link.IParcelChainLink;
import net.realmproject.dcm.parcel.impl.parcel.IParcel;
import net.realmproject.dcm.parcel.impl.transform.IParcelTransformLink;


public class Stats {

	public static void main(String[] args) {

	
		ParcelTransformLink timestamper = new IParcelTransformLink();
		timestamper.setId("timestamp");
		timestamper.setTransform(new TimeStamp().andThen(new TimeStamp()).andThen(new TimeStamp()));


		ParcelTransformLink helloworld = new IParcelTransformLink();
		helloworld.setId("hello");
		helloworld.setTransform(p -> {
			((StringBuilder)p.getPayload()).append("Hello World\n");
			return p;
		});
		
		
		ParcelTransformLink counter = new IParcelTransformLink();
		counter.setTransform(new Function<Parcel<?>, Parcel<?>>() {

			int count = 0;
			
			@Override
			public Parcel<?> apply(Parcel<?> p) {
				((StringBuilder)p.getPayload()).append(count++ + "\n");
				return p;
			}
		});
		
		
		
		
//		timestamper
//			.link(helloworld)
//			.link(counter);

		ParcelLink chain = new IParcelChainLink(timestamper, helloworld, counter);
		
		
		
		int i = 0;
		while (i < 10) {
			StringBuilder sb = new StringBuilder();
			chain.receive(new IParcel<>().payload(sb));
			System.out.println(sb.toString());
			System.out.println("*********************");
			i++;
		}
		
		
		
	}
}
