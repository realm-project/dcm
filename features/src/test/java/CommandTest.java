import org.junit.Test;

import junit.framework.Assert;
import net.realmproject.dcm.features.command.Command;
import net.realmproject.dcm.util.DCMSerialize;


public class CommandTest {

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

        Assert.assertNotNull(command.getPropertyMap());
        Assert.assertEquals("f", command.getAction());
        Assert.assertEquals("value1", command.getProperty("arg1"));

    }

}
