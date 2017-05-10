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

package net.realmproject.dcm.parcel.impl.parcel;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.util.DCMUtil;



public class IParcel<S> implements Parcel<S> {

    private static final long serialVersionUID = 1L;

    private String sourceId, targetId;
    private S payload;
    private String name;
    private long timestamp = System.currentTimeMillis();
    
    private Serializer<S> payloadSerializer = new SerializableSerializer<>();



	private String zone = null;
    private boolean localParcel = false;
    private List<String> route = new LinkedList<>();
    
    private String id = DCMUtil.generateId();

    /**************************************************************************/

    public IParcel() {
        this(null, null, null, System.currentTimeMillis());
    }

    /**
     * Creates a new Parcel with no payload and a current timestamp
     * 
     * @param type
     *            The type of this message
     * @param sourceId
     *            The id of the originating device
     */
    public IParcel(String sourceId) {
        this(sourceId, null, null, System.currentTimeMillis());
    }

    /**
     * Creates a new Parcel with the given payload and timestamp
     * 
     * @param type
     *            The type of this message
     * @param sourceId
     *            The id of the originating device
     * @param targetId
     *            The id of the target device
     * @param value
     *            The payload for this parcel
     */
    public IParcel(String sourceId, String targetId, S value) {
        this(sourceId, targetId, value, System.currentTimeMillis());
    }

    /**
     * Creates a new Parcel with the given payload and timestamp
     * 
     * @param type
     *            The type of this message
     * @param sourceId
     *            The id of the originating device
     * @param targetId
     *            The id of the target device
     * @param value
     *            The payload for this parcel
     * @param timestamp
     *            The timestamp this parcel was issued at
     */
    public IParcel(String sourceId, String targetId, S value, long timestamp) {
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.payload = value;
        this.timestamp = timestamp;
    }


    @Override
    public S getPayload() {
        return payload;
    }

    @Override
    public void setPayload(S payload) {
        this.payload = payload;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String getZone() {
        return zone;
    }

    @Override
    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public boolean isLocal() {
        return localParcel;
    }

    @Override
    public void setLocal(boolean local) {
        this.localParcel = local;
    }

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
    
    @Override
    public Parcel<S> deepCopy() {
    	Parcel<S> copy = Parcel.deserializeParcel(serializeParcel());
    	copy.setPayload(payload);
        return copy;
    }

    
    @Override
    public byte[] serializeParcel() {
    	
    	S payload = this.payload;
    	this.payload = null;
    	
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.writeObject(getPayloadSerializer().serialize(payload));
            return baos.toByteArray();

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
        	this.payload = payload;
        }

    }



    
    @Override
    public IParcel<S> shallowCopy() {
        IParcel<S> copy = new IParcel<>();
        copy.timestamp = timestamp;
        copy.sourceId = sourceId;
        copy.targetId = targetId;
        copy.payload = payload;
        copy.zone = zone;
        copy.localParcel = localParcel;
        copy.id = id;

        // copy the elements of the route stack, rather than the stack itself
        copy.route.addAll(route);

        return copy;
    }

    @Override
    public String getSourceId() {
        return sourceId;
    }

    @Override
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public String getTargetId() {
        return targetId;
    }

    @Override
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    @Override
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public List<String> getRoute() {
        return route;
    }

    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("IParcel ");
        sb.append(" | From ");
        sb.append(getSourceId());

        if (getTargetId() != null) {
            sb.append(" To ");
            sb.append(getSourceId());
        }

        sb.append(" | Time ");
        sb.append(getTimestamp());

        sb.append(" | Via ");
        sb.append(getRoute().stream().collect(Collectors.joining(">")));

        sb.append("]");

        return sb.toString();

    }

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	
	//@Override
    public Serializer<S> getPayloadSerializer() {
		return payloadSerializer;
	}

	//@Override
	public void setPayloadSerializer(Serializer<S> payloadSerializer) {
		this.payloadSerializer = payloadSerializer;
	}
	


	@Override
	public void setPayloadSerialized(Serializable payload) {
		this.payload = getPayloadSerializer().deserialize(payload);
	}

	@Override
	public Serializable getPayloadSerialized() {
		return getPayloadSerializer().serialize(payload);
	}

	

}
