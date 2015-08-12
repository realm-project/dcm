package net.realmproject.dcm.features.command;


import net.realmproject.dcm.event.Device;
import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.FilterBuilder;
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
public abstract class ICommandDevice<T extends State> extends Device implements Commands, Statefulness<T> {

    private CommandDispatcher dispatcher;

    public ICommandDevice(String id, DeviceEventBus bus) {
        super(id, bus);
        dispatcher = new CommandDispatcher(this, bus);
        bus.subscribe(FilterBuilder.start().eventGet().target(getId()), event -> publishState());
    }

    @Override
    public CommandDispatcher getCommandDispatcher() {
        return dispatcher;
    }

}
