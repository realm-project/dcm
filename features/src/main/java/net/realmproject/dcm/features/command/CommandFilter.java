package net.realmproject.dcm.features.command;


import net.realmproject.dcm.features.message.MessageTypeFilter;


public class CommandFilter extends MessageTypeFilter {

    public CommandFilter() {
        super(Command.COMMAND_MESSAGE_TYPE);
    }

}
