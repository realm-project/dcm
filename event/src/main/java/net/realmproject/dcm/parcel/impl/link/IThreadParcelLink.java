package net.realmproject.dcm.parcel.impl.link;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.link.ParcelLink;
import net.realmproject.dcm.util.DCMThreadPool;

public class IThreadParcelLink extends IParcelLink implements ParcelLink {

	private BlockingQueue<Parcel<?>> queue = new LinkedBlockingQueue<>();
	
	public IThreadParcelLink() {
		DCMThreadPool.getPool().submit(() -> {
			while (true) {
				Parcel<?> parcel = queue.take();
				send(parcel);
			}
		});
	}
	
	@Override
	public void receive(Parcel<?> parcel) {
		queue.offer(parcel);
	}

}
