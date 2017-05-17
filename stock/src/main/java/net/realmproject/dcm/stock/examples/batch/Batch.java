package net.realmproject.dcm.stock.examples.batch;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import net.realmproject.dcm.parcel.core.ParcelLink;
import net.realmproject.dcm.parcel.impl.flow.IParcelLoadBalancer;
import net.realmproject.dcm.parcel.impl.link.IThreadParcelLink;
import net.realmproject.dcm.parcel.impl.service.IParcelService;
import net.realmproject.dcm.util.DCMThreadPool;

public class Batch {

	public static void main(String[] args) throws InterruptedException, ExecutionException {


		//Create three processing/batch worker threads
		Incrementer b1 = new Incrementer();
		ParcelLink t1 = new IThreadParcelLink(b1);

		Incrementer b2 = new Incrementer();
		ParcelLink t2 = new IThreadParcelLink(b2);

		Incrementer b3 = new Incrementer();
		ParcelLink t3 = new IThreadParcelLink(b3);

		
		//Create a load balancer with default round-robin
		IParcelLoadBalancer loadBalancer = new IParcelLoadBalancer(t1, t2, t3);

		//Create service front-end
		IParcelService<Integer, Integer> incrementService = new IParcelService<>(loadBalancer);

		
		//link the batch processors back to the the service to return their results
		b1.setReceiver(incrementService);
		b2.setReceiver(incrementService);
		b3.setReceiver(incrementService);
		
		
		//Submit "jobs"
		for (int i = 0; i < 20; i++) {
			
			
			//call the service
			Future<Integer> future = incrementService.call(i);
			
			//create a thread to wait on the result and print it.
			DCMThreadPool.getPool().submit(() -> {
				try {
					System.out.println(future.get());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	
		
		Thread.sleep(100000);

	}

}
