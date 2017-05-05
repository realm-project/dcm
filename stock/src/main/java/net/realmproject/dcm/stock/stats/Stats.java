package net.realmproject.dcm.stock.stats;

import net.realmproject.dcm.parcel.IParcel;
import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.parcel.flow.relay.IParcelRelay;
import net.realmproject.dcm.parcel.flow.relay.ParcelRelay;


public class Stats {

	public static void main(String[] args) {

		
		ParcelRelay node = new IParcelRelay(null);
		node.setId("stats");
		node.setTransform(new TimeStamp().andThen(new TimeStamp()).andThen(new TimeStamp()));

		
		int i = 0;
		while (i < 10) {
			StringBuilder sb = new StringBuilder();
			Parcel<?> sbp = new IParcel<>().payload(sb);
			node.receive(new IParcel<>().payload(sb));
			System.out.println(sb.toString());
			System.out.println("*********************");
			i++;
		}
		
		
		
	}
}
