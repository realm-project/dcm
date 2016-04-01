package net.realmproject.dcm.util.backoff;


import java.util.Iterator;
import java.util.function.Function;


/**
 * Component which implements a backoff function timing delays. This is useful
 * for implementing logic for reconnecting to a network resource. By default,
 * the function used is a truncated binary exponential backoff function capped
 * at 10 minutes. The default function can be replaced by another function which
 * transforms attempt number n into delay time t in milliseconds. This class
 * also implements the {@link Iterator} interface for accessing delay times.
 * 
 * @author NAS
 *
 */
public class BackoffGenerator implements Iterator<Long> {

    private int attempt = 0;
    private Function<Integer, Long> backoffFunction;

    /**
     * Creates a new backoff object with an initial delay of 1 second and a
     * maximum delay of 600 seconds (10 minutes)
     */
    public BackoffGenerator() {
        this(1, 600);
    }

    /**
     * Creates a new backoff object with an initial (minimum) and maximum
     * delays. Actual delay will be <tt>max(initial, min(delay, maximum))</tt>
     * 
     * @param initial
     *            the initial/minimum time to wait.
     * @param maximum
     *            the maximum time to wait
     */
    public BackoffGenerator(long initial, long maximum) {
        this(i -> {
            long k = (long) Math.floor(Math.random() * Math.pow(2, i));
            return 1000 * Math.max(initial, Math.min(k, maximum));
        });
    }

    /**
     * Creates a new backoff object with a custom delay function
     * 
     * @param backoffFunction
     *            custom delay function accepting attemt # and returning delay
     *            in ms
     */
    public BackoffGenerator(Function<Integer, Long> backoffFunction) {
        setBackoffFunction(backoffFunction);
    }

    /**
     * Resets the attempt counter to 0.
     */
    public void reset() {
        attempt = 0;
    }

    /**
     * Performs a delay of a length obtained by calling
     * {@link BackoffGenerator#getDelay} by calling {@link Thread#sleep(long)}
     * 
     * @throws InterruptedException
     */
    public void delay() throws InterruptedException {
        Thread.sleep(getDelay());
    }

    /**
     * Applies the attempt counter to the backoff function, incrementing the
     * counter in the process
     * 
     * @return the result of the backoff function
     */
    public long getDelay() {
        return backoffFunction.apply(attempt++);
    }

    /**
     * Returns the backoff function. Note that this functon contains no state
     * about the number of attempts.
     * 
     * @return the backoff function used to calculate delays.
     */
    public Function<Integer, Long> getBackoffFunction() {
        return backoffFunction;
    }

    /**
     * Sets the backoff function used to calculate delays. Note that setting the
     * backoff function does not reset the attempt counter.
     * 
     * @param backoffFunction
     *            the new backoff function
     */
    public void setBackoffFunction(Function<Integer, Long> backoffFunction) {
        this.backoffFunction = backoffFunction;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Long next() {
        return getDelay();
    }

}
