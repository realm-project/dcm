package net.realmproject.dcm.features.ping;


import java.io.Serializable;
import java.util.Date;

import net.realmproject.dcm.parcel.core.Identity;
import net.realmproject.dcm.util.DCMUtil;


/**
 * Class containing data used in PING/PONG messages
 * 
 * @author NAS
 *
 */
public class Ping implements Serializable, Identity {

    private String id;
    private Date start, end;

    public Ping() {
        id = DCMUtil.generateId();
        start = new Date();
    }

    public long completed() {
        if (end == null) {
            end = new Date();
        }
        return getPingTime();
    }

    public long getPingTime() {
        if (end == null) { return -1l; }

        return end.getTime() - start.getTime();

    }

    public boolean startedAfter(Ping other) {
        return start.after(other.start);
    }

    public boolean startedBefore(Ping other) {
        return start.before(other.start);
    }

    public boolean equals(Object o) {
        if (!(o instanceof Ping)) { return false; }
        Ping p = (Ping) o;
        return id.equals(p.id);

    }

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

}
