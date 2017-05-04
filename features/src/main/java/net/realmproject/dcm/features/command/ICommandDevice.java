package net.realmproject.dcm.features.command;


import net.realmproject.dcm.features.stateful.State;
import net.realmproject.dcm.features.stateful.StateQuery;
import net.realmproject.dcm.features.stateful.StatefulDevice;
import net.realmproject.dcm.parcel.flow.hub.ParcelHub;
import net.realmproject.dcm.parcel.node.filter.FilterBuilder;
import net.realmproject.dcm.parcel.node.publisher.IParcelPublisher;
import net.realmproject.dcm.parcel.node.receiver.IParcelConsumer;


/**
 * Implementation of Statefulness and Commands features.
 * 
 * @author NAS
 *
 * @param <T>
 *            type of device state
 */
public abstract class ICommandDevice<T extends State> extends IParcelPublisher implements CommandDevice, StatefulDevice<T> {

    private CommandDispatcher dispatcher;

    public ICommandDevice(String id, ParcelHub bus) {
        super(id, bus);
        dispatcher = new CommandDispatcher(this, bus);
        bus.subscribe(FilterBuilder.start().payload(StateQuery.class).target(getId()), new IParcelConsumer(getId(), event -> publishState()));
        
    }

    @Override
    public CommandDispatcher getCommandDispatcher() {
        return dispatcher;
    }

}
