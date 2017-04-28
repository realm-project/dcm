package net.realmproject.dcm.features.command;


import net.realmproject.dcm.features.stateful.State;
import net.realmproject.dcm.features.stateful.StateQuery;
import net.realmproject.dcm.features.stateful.StatefulDevice;
import net.realmproject.dcm.parcel.Device;
import net.realmproject.dcm.parcel.bus.ParcelHub;
import net.realmproject.dcm.parcel.filter.FilterBuilder;


/**
 * Implementation of Statefulness and Commands features.
 * 
 * @author NAS
 *
 * @param <T>
 *            type of device state
 */
public abstract class ICommandDevice<T extends State> extends Device implements CommandDevice, StatefulDevice<T> {

    private CommandDispatcher dispatcher;

    public ICommandDevice(String id, ParcelHub bus) {
        super(id, bus);
        dispatcher = new CommandDispatcher(this, bus);
        bus.subscribe(FilterBuilder.start().payload(StateQuery.class).target(getId()), event -> publishState());
        
    }

    @Override
    public CommandDispatcher getCommandDispatcher() {
        return dispatcher;
    }

}
