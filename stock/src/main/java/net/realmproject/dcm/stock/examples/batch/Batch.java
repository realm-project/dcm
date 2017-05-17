package net.realmproject.dcm.stock.examples.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.link.ParcelLink;
import net.realmproject.dcm.parcel.core.service.ParcelService;
import net.realmproject.dcm.parcel.impl.flow.IParcelLoadBalancer;
import net.realmproject.dcm.parcel.impl.link.IParcelChainLink;
import net.realmproject.dcm.parcel.impl.link.IThreadParcelLink;
import net.realmproject.dcm.parcel.impl.service.IParcelService;
import net.realmproject.dcm.parcel.impl.transform.IParcelTransformLink;
import net.realmproject.dcm.util.DCMThreadPool;

public class Batch {

	public static final int THREADS = 4;
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		
		//Increment function which sleeps for 1 second to simulate work
		Function<Parcel<?>, Parcel<?>> incrementer = p -> {
			Parcel<Integer> parcel = (Parcel<Integer>) p;
			Integer v = parcel.getPayload();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			parcel.setPayload(v+1);
			return parcel;
		}; 
		
		
		
		
		//Create service front-end
		ParcelService<Integer, Integer> incrementService = new IParcelService<>();
		
		
		//Create a load balancer with default round-robin
		IParcelLoadBalancer loadBalancer = new IParcelLoadBalancer();
		
		
		
		//Generate thread/processing nodes
		List<ParcelLink> workers = new ArrayList<>();
		for (int i = 0; i < THREADS; i++) {
			ParcelLink worker = new IParcelChainLink(
				new IThreadParcelLink(),
				new IParcelTransformLink(incrementer)
			);
			workers.add(worker);
		}

		
		
		//Link the nodes together
		incrementService.link(loadBalancer);
		loadBalancer.setReceivers(workers);
		for (ParcelLink worker : workers) {
			worker.link(incrementService);
		}
		
		
		
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
	
		//make sure the program doesn't terminate too soon
		Thread.sleep(20000);

	}

}
