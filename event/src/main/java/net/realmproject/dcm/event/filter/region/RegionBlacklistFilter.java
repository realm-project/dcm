package net.realmproject.dcm.event.filter.region;


import java.util.List;

import net.realmproject.dcm.event.bus.DeviceEventBus;
import net.realmproject.dcm.event.filter.NegationFilter;


/**
 * DeviceEvent filter which blocks events from a certain region. See
 * {@link DeviceEventBus}
 * 
 * @author NAS
 *
 */

public class RegionBlacklistFilter extends NegationFilter {

    public RegionBlacklistFilter(String region) {
        super(new RegionWhitelistFilter(region));
    }

    public RegionBlacklistFilter(List<String> regions) {
        super(new RegionWhitelistFilter(regions));
    }

}
