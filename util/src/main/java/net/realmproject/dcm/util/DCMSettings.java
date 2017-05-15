package net.realmproject.dcm.util;

public class DCMSettings {

	/**
	 * All nodes should be created by this point. CREATION_DELAY represents the
	 * time it takes for all nodes to be created. This interval is a useful wait
	 * period for nodes which rely on other nodes to perform their own startup
	 * tasks. For example, a client socket waiting for another node to start a
	 * server socket before trying to connect.
	 */
	public static int CREATION_DELAY = 2;

	/**
	 * All nodes should be ready by this point. STARTUP_DELAY represents the
	 * time it takes for all nodes to initialize into an operating state. This
	 * value is larger than CREATION_DELAY and is counted from program start
	 * (T=0) rather than after CREATION_DELAY. It is a grace period for nodes
	 * which depend on the creation of other nodes before performing their own
	 * startup tasks. For example, a client socket waiting for another node to
	 * start a server socket should have made its connection by the time
	 * STARTUP_DELAY is reached.
	 */
	public static int STARTUP_DELAY = 4;

	/**
	 * The number of threads for the DCMThreadPool's Scheduled ThreadPool to
	 * have.
	 */
	public static int SCHEDULED_THREADS = 10;
}
