package net.realmproject.dcm.command;

import java.util.HashMap;
import java.util.Map;

public class Command {

    public String action;
    public Map<String, Object> arguments = new HashMap<>();
    public boolean record;
    
}
