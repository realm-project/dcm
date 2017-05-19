package net.realmproject.dcm.features.command;


import net.realmproject.dcm.features.stateful.State;
import net.realmproject.dcm.features.stateful.StateQuery;
import net.realmproject.dcm.features.stateful.StatefulDevice;
import net.realmproject.dcm.parcel.core.flow.filter.ParcelFilterLink;
import net.realmproject.dcm.parcel.core.flow.hub.ParcelHub;
import net.realmproject.dcm.parcel.core.flow.link.ParcelLink;
import net.realmproject.dcm.parcel.impl.flow.filter.FilterBuilder;
import net.realmproject.dcm.parcel.impl.flow.filter.IParcelFilterLink;
import net.realmproject.dcm.parcel.impl.receiver.IParcelConsumer;
import net.realmproject.dcm.parcel.impl.sender.IParcelSender;


/**
 * Implementation of Statefulness and Commands features.
 * 
 * @author NAS
 *
 * @param <T>
 *            type of device state
 */
public abstract class ICommandDevice<T extends State> extends IParcelSender implements CommandDevice, StatefulDevice<T> {

    private CommandDispatcher dispatcher;

    public ICommandDevice(String id, ParcelHub bus) {
        super(id, bus);
        dispatcher = new CommandDispatcher(this, bus);
        ParcelLink filter = new IParcelFilterLink(FilterBuilder.start().payload(StateQuery.class).target(getId()));
        bus.link(filter).link(new IParcelConsumer(getId(), event -> publishState()));
        
    }

    @Override
    public CommandDispatcher getCommandDispatcher() {
        return dispatcher;
    }

}
