/**
 * Copyright 2014 Realm Project
 * 
 * This file is part of the Device Control Module (DCM).
 * 
 * DCM is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DCM is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DCM. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package net.realmproject.dcm.network.mq.jms;


import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import net.realmproject.dcm.network.impl.IWireReceiver;
import net.realmproject.dcm.parcel.core.Logging;
import net.realmproject.dcm.parcel.core.ParcelReceiver;


/**
 * @author maxweld
 *
 */
public class JmsWireReceiver extends IWireReceiver implements MessageListener, Logging {

    public JmsWireReceiver(ParcelReceiver receiver) {
        super(receiver);
    }

    @Override
    public void onMessage(Message message) {
        try {
            BytesMessage msg = (BytesMessage) message;
            byte[] bytes = new byte[(int) msg.getBodyLength()];
            msg.readBytes(bytes);
            receive(bytes);
        }
        catch (JMSException e) {
            getLog().warn(e.getMessage());
        }
        catch (ClassCastException e) {
            getLog().warn("Received message cannot be read.");
        }
    }

}
