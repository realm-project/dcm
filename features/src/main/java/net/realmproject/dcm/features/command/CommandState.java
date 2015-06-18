package net.realmproject.dcm.features.command;


import net.realmproject.dcm.features.Statefulness.State;


public class CommandState extends State {

    private String commandId;

    public String commandId() {
        return commandId;
    }

    public void setCommandId(String id) {
        this.commandId = id;
    }

}
