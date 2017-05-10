package net.realmproject.dcm.stock.stats;

import net.realmproject.dcm.parcel.IParcel;
import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.node.link.IParcelLink;
import net.realmproject.dcm.parcel.node.link.ParcelLink;
import net.realmproject.dcm.parcel.node.transform.IParcelTransformLink;
import net.realmproject.dcm.parcel.node.transform.ParcelTransformLink;


public class Stats {

	public static void main(String[] args) {

		
		ParcelTransformLink node2 = new IParcelTransformLink(null);
		node2.setId("hello");
		node2.setTransform(p -> {
			((StringBuilder)p.getPayload()).append("Hello World\n");
			return p;
		});
		
		
		
	
		ParcelTransformLink node1 = new IParcelTransformLink(node2);
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
