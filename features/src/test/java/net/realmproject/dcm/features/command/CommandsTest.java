package net.realmproject.dcm.features.command;


import java.io.Serializable;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import net.realmproject.dcm.parcel.bus.IParcelHub;
import net.realmproject.dcm.parcel.bus.ParcelHub;
import net.realmproject.dcm.util.DCMSerialize;


public class CommandsTest {

    @Test
    public void testIncrement() {

    	ParcelHub bus = new IParcelHub();
        TestCommandDevice device = new TestCommandDevice("device", bus);

        Assert.assertEquals(0, device.getState().number);
        device.runCommand(new Command("increment"));
        Assert.assertEquals(1, device.getState().number);

        // testing single argument function
        device.runCommand(new Command("incrementBy").property("value", 2));
        Assert.assertEquals(3, device.getState().number);

        // testing string argument
        // don't name our arg here to test default arg name version of method
        device.runCommand(new Command("incrementFromString").property("1"));
        Assert.assertEquals(4, device.getState().number);

        // testing structs
        device.runCommand(new Command("incrementByIncrementer").property("value", new Incrementer(1)));
        Assert.assertEquals(5, device.getState().number);

        // testing single argument without annotation as fields in a single
        // struct
        device.runCommand(
                new Command("incrementByIncrementerUnannotated", DCMSerialize.structToMap(new Incrementer(2))));
        Assert.assertEquals(7, device.getState().number);

        // testing structs which have been serialized by
        // DCMSerialize.structToMap
        device.runCommand(new Command("incrementByIncrementer")
                .property((Serializable) DCMSerialize.structToMap(new Incrementer(1))));
        Assert.assertEquals(8, device.getState().number);

        // testing serializing integers with default DSMSerialise.structToMap
        // key name of "value"
        Map<String, Serializable> intMap = DCMSerialize.structToMap(1);
        device.runCommand(new Command("incrementBy", intMap));
        Assert.assertEquals(9, device.getState().number);

        // testing multi-arg functions
        device.runCommand(new Command("incrementTwice").property("first", 3).property("second", "3"));
        Assert.assertEquals(15, device.getState().number);

        // testing default @Arg name of "value"
        device.runCommand(new Command("incrementUnnamed", DCMSerialize.structToMap(3)));
        Assert.assertEquals(18, device.getState().number);

        // testing un-named arg with simple integer value
        device.runCommand(new Command("incrementUnnamed").property("value", 1));
        Assert.assertEquals(19, device.getState().number);

    }

    @Test
    public void fromJson() {

        //@formatter:off
        
        String json = ""
                + "{"
                + " \"action\": \"f\",\n"
                + " \"arguments\":{\n"
                + "     \"arg1\": \"value1\"\n"
                + " }\n"
                + "}\n";
        
        //@formatter:on

        Command command = DCMSerialize.deserialize(json, Command.class);

        Assert.assertNotNull(command.getProperties());
        Assert.assertEquals("f", command.getAction());
        Assert.assertEquals("value1", command.getProperty("arg1"));

    }

    @Test
    public void testId() {
        Command command = new Command();
        Assert.assertNotNull("Command ID should be automatically generated on construction", command.getId());
    }
}
