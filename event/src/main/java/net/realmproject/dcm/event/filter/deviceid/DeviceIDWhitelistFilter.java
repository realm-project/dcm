package net.realmproject.dcm.event.filter.deviceid;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.realmproject.dcm.event.IDeviceEvent;


/**
 * DeviceEvent filter which only accepts events with certain device ids
 * 
 * @author NAS
 *
 */

public class DeviceIDWhitelistFilter implements Predicate<IDeviceEvent> {

    List<String> ids;

    public DeviceIDWhitelistFilter(String id) {
        ids = new ArrayList<>();
        ids.add(id);
    }

    public DeviceIDWhitelistFilter(List<String> ids) {
        ids = new ArrayList<>(ids);
    }

    @Override
    public boolean test(IDeviceEvent e) {
        return ids.contains(e.getDeviceId());
    }

}