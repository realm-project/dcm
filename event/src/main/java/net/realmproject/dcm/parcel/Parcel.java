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

package net.realmproject.dcm.parcel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import net.realmproject.dcm.parcel.bus.ParcelHub;
import net.realmproject.dcm.parcel.identity.Identity;
import net.realmproject.dcm.parcel.identity.SourceIdentity;
import net.realmproject.dcm.parcel.identity.TargetIdentity;

/**
 * A Parcel represents a message in a message-passing node graph. It optionally
 * contains a {@link Serializable} payload.
 * 
 * @author NAS
 *
 */
public interface Parcel<S> extends Serializable, Identity, TargetIdentity, SourceIdentity {

	/**
	 * Gets the current payload for this Parcel.
	 * 
	 * @param <S>
	 *            the actual type of the payload. This method will attempt to
	 *            cast the payload to match this type parameter.
	 * 
	 * @return The current payload. May be null.
	 */
	S getPayload();

	/**
	 * Sets the payload for this Parcel.
	 * 
	 * @param payload
	 *            The new payload. May be null.
	 */
	void setPayload(S payload);

	/**
	 * Gets the time that this parcel was published (according to the computer
	 * which published it). The time is represented as a unix epoch time with
	 * millisecond precision as returned by {@link System#currentTimeMillis()}.
	 * By default, this timestamp is set to the time Parcel was created
	 * 
	 * @return date of publication
	 */
	long getTimestamp();

	/**
	 * Sets the time that this parcel was published (according to the computer
	 * which published it).
	 * 
	 * @param timestamp
	 *            the new timestamp for this parcel
	 */
	default void setTimestamp(Date timestamp) {
		setTimestamp(timestamp.getTime());
	}

	/**
	 * Sets the time that this parcel was published (according to the computer
	 * which published it). The time is represented as a unix epoch time with
	 * millisecond precision as returned by {@link System#currentTimeMillis()}
	 * 
	 * @param timestamp
	 *            the new timestamp for this parcel
	 */
	void setTimestamp(long timestamp);

	/**
	 * Gets the zone this parcel was published in
	 * 
	 * @return zone of origin
	 */
	String getZone();

	/**
	 * The zone property is an arbitrary string representing what part of a
	 * larger network of devices the parcel originated from, and is usually not
	 * set by the publishing device, but by a component like a {@link ParcelHub}
	 * 
	 * @param zone
	 *            the zone this parcel originates from
	 */
	void setZone(String zone);

	/**
	 * Local parcels are parcels which are not meant to be propagated beyond the
	 * zone they were emitted in. {@link ParcelHub} implementations should not
	 * broadcast local parcels from other zones.
	 * 
	 * @return true if this parcel is local, false otherwise.
	 */
	boolean isLocal();

	/**
	 * Sets this parcel as local.
	 * 
	 * @param local
	 *            indicates if this parcel is local.
	 */
	void setLocal(boolean local);

	/**
	 * Fluent API convenience method. See {@link Parcel#setSourceId(String)}
	 * 
	 * @param sourceId
	 *            the id of the originating node
	 * @return This Parcel
	 */
	default Parcel<S> sourceId(String sourceId) {
		setSourceId(sourceId);
		return this;
	}

	/**
	 * Fluent API convenience method. See {@link Parcel#setTargetId(String)}
	 * 
	 * @param targetId
	 *            the id of the target node. To set no target id, pass null
	 * @return This Parcel
	 */
	default Parcel<S> targetId(String targetId) {
		setTargetId(targetId);
		return this;
	}

	/**
	 * Fluent API convenience method. See {@link Parcel#setPayload(S)}
	 * 
	 * @param payload
	 *            the (optional) payload for this parcel
	 * @return This Parcel
	 */
	default Parcel<S> payload(S payload) {
		setPayload(payload);
		return this;
	}

	/**
	 * Fluent API convenience method. See {@link Parcel#setTimestamp(Date)}
	 * 
	 * @param timestamp
	 *            the timestamp as a Date object
	 * @return This Parcel
	 */
	default Parcel<S> timestamp(Date timestamp) {
		setTimestamp(timestamp);
		return this;
	}

	/**
	 * Fluent API convenience method. See {@link Parcel#setTimestamp(long)}
	 * 
	 * @param timestamp
	 *            the timestamp this parcel was issued at
	 * @return This Parcel
	 */
	default Parcel<S> timestamp(long timestamp) {
		setTimestamp(timestamp);
		return this;
	}

	/**
	 * Fluent API convenience method. See {@link Parcel#setZone(String)}
	 * 
	 * @param zone
	 *            The zone this parcel originates from
	 * @return This Parcel
	 */
	default Parcel<S> zone(String zone) {
		setZone(zone);
		return this;
	}

	/**
	 * Fluent API convenience method. See {@link Parcel#setLocal(boolean)}
	 * 
	 * @param local
	 *            flag to set this parcel local or not
	 * @return This Parcel
	 */
	default Parcel<S> local(boolean local) {
		setLocal(local);
		return this;
	}

	/**
	 * Retrieves the route this parcel has traveled
	 * 
	 * @return the stack of nodes this parcel has traversed
	 */
	public List<String> getRoute();

	
	/**
	 * Performs a deep copy of this Parcel including making a copy of the
	 * payload.
	 * 
	 * @return The deep copy of this Parcel
	 */
	public Parcel<S> deepCopy();

	/**
	 * Performs a shallow copy of this Parcel without making a copy of the
	 * payload.
	 * 
	 * @return The shallow copy of this Parcel
	 */
	public Parcel<S> shallowCopy();

	/**
	 * Loads the base information from this Parcel into the given parsel. This
	 * is used mostly to switch from one Parcel implementation to another
	 * 
	 * @param p
	 *            the Parcel to load this Parcel's values into
	 */
	default void derive(Parcel<?> p) {
		p.setId(getId());
		p.setLocal(isLocal());
		p.setSourceId(getSourceId());
		p.setTargetId(getTargetId());
		p.setTimestamp(getTimestamp());
		p.setZone(getZone());
	}

	//public Serializer<S> getPayloadSerializer();
	//public void setPayloadSerializer(Serializer<S> payloadSerializer);

	/**
	 * Sets the payload from a serialized form. The payload must be in a format
	 * that the Parcel understands. This is useful for allowing Parcels to
	 * specify their own payload serialization strategies for transmission
	 * across a network. If the parcel and payload are both serialized, the
	 * parcel can be deserialized first, and have the payload loaded back into
	 * it via a custom serialization mechanism.
	 * 
	 * @param payload
	 *            A serialized form of a payload.
	 */
	public void setPayloadSerialized(Serializable payload);

	/**
	 * Returns a Serialized copy of the payload for this Parcel. This is useful
	 * for allowing Parcels to specify their own payload serialization
	 * strategies for transmission across a network.
	 * 
	 * @return
	 */
	public Serializable getPayloadSerialized();

	/**
	 * Serialize this Parcel to a byte array, but without it's payload.
	 * @return
	 */
	byte[] serializeParcel();

	public static <S> Parcel<S> deserializeParcel(byte[] input) {

		try {

			ByteArrayInputStream bais = new ByteArrayInputStream(input);
			ObjectInputStream ois = new ObjectInputStream(bais);
			Parcel<S> parcel = (Parcel<S>) ois.readObject();
			Serializable payload = (Serializable) ois.readObject();
			parcel.setPayloadSerialized(payload);
			return parcel;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}