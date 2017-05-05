package net.realmproject.dcm.stock.stats;

import net.realmproject.dcm.parcel.IParcel;
import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.flow.relay.IParcelRelay;
import net.realmproject.dcm.parcel.flow.relay.ParcelRelay;


public class Stats {

	public static void main(String[] args) {

		
		ParcelRelay node2 = new IParcelRelay(null);
		node2.setId("hello");
		node2.setTransform(p -> {
			((StringBuilder)p.getPayload()).append("Hello World\n");
			return p;
		});
		
		ParcelRelay node1 = new IParcelRelay(node2);
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
