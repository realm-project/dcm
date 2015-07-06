package net.realmproject.dcm.device;


import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.features.command.CommandDispatcher;
import net.realmproject.dcm.features.command.Commands;
import net.realmproject.dcm.features.statefulness.State;
import net.realmproject.dcm.features.statefulness.Statefulness;


/**
 * Implementation of Statefulness and Commands features.
 * 
 * @author NAS
 *
 * @param <T>
 *            type of device state
 */
public abstract class CommandDevice<T extends State> extends Device implements Commands, Statefulness<T> {

    private CommandDispatcher dispatcher;

    public CommandDevice(String id, DeviceEventBus bus) {
        super(id, bus);
        dispatcher = new CommandDispatcher(this, bus);
    }

    @Override
    public CommandDispatcher getCommandDispatcher() {
        return dispatcher;
    }

}
