package net.realmproject.dcm.command;


import java.util.HashMap;
import java.util.Map;


public class Command {

    /**
     * The name of the action or command to invoke on the {@link CommandDevice}
     */
    public String action;

    /**
     * The arguments to invoke the command with
     */
    public Map<String, Object> arguments = new HashMap<>();

    /**
     * Indicates if this command should be recorded (if such facilities are
     * available)
     */
    public boolean record;

}
