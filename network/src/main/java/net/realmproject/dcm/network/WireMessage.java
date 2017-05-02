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

package net.realmproject.dcm.network;


import java.io.Serializable;

import net.realmproject.dcm.parcel.Parcel;
import net.realmproject.dcm.util.DCMUtil;


/**
 * 
 * Wire-format object for device communication
 * 
 * @author maxweld
 *
 */
public class WireMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private byte[] serializedParcel;
    private String messageId = DCMUtil.generateId();

    public WireMessage() {}

    public WireMessage(Parcel<?> parcel) {
        setParcel(parcel);
    }

    public Parcel<?> getParcel() {
        Parcel<?> p = Parcel.deserializeParcel(serializedParcel);
        return p;        
    }

    public void setParcel(Parcel<?> parcel) {
    	this.serializedParcel = parcel.serializeParcel();
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String id) {
        this.messageId = id;
    }

}
