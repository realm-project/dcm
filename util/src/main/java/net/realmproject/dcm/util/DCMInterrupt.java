package net.realmproject.dcm.util;


import java.util.function.Consumer;


public class DCMInterrupt {

    @FunctionalInterface
    public interface DCMInterruptible {

        void run() throws Exception;
    }

    /**
     * Run a chunk of code and swallow all exceptions <i>except</i>
     * {@link InterruptedException}, for which it calls
     * <tt>Thread.currentThread().interrupt();</tt> first.
     * 
     * @param interruptible
     *            the code to execute
     */
    public static void handle(DCMInterruptible interruptible) {
        handle(interruptible, e -> {});
    }

    /**
     * Run a chunk of code and route all exceptions to <tt>handler</tt>
     * <i>except</i> {@link InterruptedException}, for which it calls
     * <tt>Thread.currentThread().interrupt();</tt> instead.
     * 
     * @param interruptible
     *            the code to execute
     * @param handler
     *            The error handler to pass exceptions to
     */
    public static void handle(DCMInterruptible interruptible, Consumer<Exception> handler) {

        try {
            interruptible.run();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        catch (Exception e) {
            handler.accept(e);
        }
    }

    /**
     * Run a chunk of code and route all exceptions to <tt>handler</tt>
     * <i>except</i> {@link InterruptedException}, for which it calls
     * <tt>Thread.currentThread().interrupt();</tt> and passes the exception to
     * interruptedHandler instead.
     * 
     * @param interruptible
     *            the code to execute
     * @param handler
     *            The error handler to pass exceptions to
     * @param interruptedHandler
     *            The error handler to pass InterruptedExceptions to
     */
    public static void handle(DCMInterruptible interruptible, Consumer<Exception> handler,
            Consumer<InterruptedException> interruptedHandler) {

        try {
            interruptible.run();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            interruptedHandler.accept(e);
        }
        catch (Exception e) {
            handler.accept(e);
        }
    }

}
