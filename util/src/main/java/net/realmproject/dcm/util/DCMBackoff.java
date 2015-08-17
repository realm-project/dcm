package net.realmproject.dcm.util;


import java.util.function.Function;


/**
 * Component which implements a backoff function timing delays. This is useful
 * for implementing logic for reconnecting to a network resource. By default,
 * the function used is a truncated binary exponential backoff function capped
 * at 5 minutes. The default function can be replaced by another function which
 * transforms attempt number n into delay time t in milliseconds
 * 
 * @author NAS
 *
 */
public class DCMBackoff {

    private int attempt = 0;
    private Function<Integer, Integer> backoffFunction = i -> {
        int k = (int) Math.floor(Math.random() * Math.pow(2, i));
        return 1000 * Math.max(k, 60 * 5);
    };

    public DCMBackoff() {}

    public DCMBackoff(Function<Integer, Integer> backoffFunction) {
        setBackoffFunction(backoffFunction);
    }

    public void reset() {
        attempt = 0;
    }

    public void delay() throws InterruptedException {
        Thread.sleep(backoffFunction.apply(attempt++));
    }

    public Function<Integer, Integer> getBackoffFunction() {
        return backoffFunction;
    }

    public void setBackoffFunction(Function<Integer, Integer> backoffFunction) {
        this.backoffFunction = backoffFunction;
    }

}
