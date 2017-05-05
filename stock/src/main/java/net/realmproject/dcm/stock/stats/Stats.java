package net.realmproject.dcm.stock.stats;

import net.realmproject.dcm.parcel.IParcel;
import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.flow.link.IParcelLink;
import net.realmproject.dcm.parcel.flow.link.ParcelLink;


public class Stats {

	public static void main(String[] args) {

		
		ParcelLink node2 = new IParcelLink(null);
		node2.setId("hello");
		node2.setTransform(p -> {
			((StringBuilder)p.getPayload()).append("Hello World\n");
			return p;
		});
		
		ParcelLink node1 = new IParcelLink(node2);
		node1.setId("timestamp");
		node1.setTransform(new TimeStamp().andThen(new TimeStamp()).andThen(new TimeStamp()));

		
		
		
		
		
		int i = 0;
		while (i < 10) {
			StringBuilder sb = new StringBuilder();
			Parcel<?> sbp = new IParcel<>().payload(sb);
			node1.receive(new IParcel<>().payload(sb));
			System.out.println(sb.toString());
			System.out.println("*********************");
			i++;
		}
		
		
		
	}
}
