package net.realmproject.dcm.features.command;

import net.realmproject.dcm.parcel.Logging;
import net.realmproject.dcm.parcel.node.identity.Identity;

/**
 * Interface for a device accepting {@link Command} messages and automatically
 * dispatching them to methods with {@link CommandMethod} and {@link Arg}
 * annotations.
 * 
 * @author NAS
 *
 */

public interface CommandDevice extends Identity, Logging {

    /**
     * This function is called before a {@link CommandMethod} is invoked. If
     * this method returns false, the method invocation will not proceed.
     * 
     * @param command
     *            The command which is about to be processed. This is before the
     *            command undergoes type conversion to match the argument type
     *            for the appropriate method.
     * 
     * @return true of the command should be executed, false otherwise
     */
    default boolean beforeCommand(Command command) {
        return true;
    }

    /**
     * This function is called after a {@link CommandMethod} is invoked
     * 
     * @param command
     *            The command which is about to be processed. This is before the
     *            command undergoes type conversion to match the argument type
     *            for the appropriate method.
     */
    default void afterCommand(Command command) {}

    default void runCommand(Command command) {
        getCommandDispatcher().submitCommand(command);
    }

    /**
     * Get the command dispatcher for this Device
     * 
     * @return the {@link CommandDispatcher} for this device
     */
    CommandDispatcher getCommandDispatcher();

}
