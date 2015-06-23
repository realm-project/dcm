package net.realmproject.dcm.features.ping;


import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


/**
 * Class containing data used in PING/PONG messages
 * 
 * @author NAS
 *
 */
public class Ping implements Serializable {

    private UUID uuid;
    private Date start, end;

    public Ping() {
        uuid = UUID.randomUUID();
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
        return uuid.equals(p.uuid);

    }

}
