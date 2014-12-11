package net.realmproject.dcm.event.filter.region;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.realmproject.dcm.event.IDeviceEvent;
import net.realmproject.dcm.event.bus.DeviceEventBus;


/**
 * DeviceEvent filter which only allows events from a certain region. See
 * {@link DeviceEventBus}
 * 
 * @author NAS
 *
 */

public class RegionWhitelistFilter implements Predicate<IDeviceEvent> {

    private List<String> whitelist;

    public RegionWhitelistFilter(String region) {
        whitelist = new ArrayList<>();
        whitelist.add(region);
    }

    public RegionWhitelistFilter(List<String> regions) {
        whitelist = new ArrayList<>(regions);
    }

    @Override
    public boolean test(IDeviceEvent e) {
        return whitelist.contains(e.getRegion());
    }
}
