package net.realmproject.dcm.event.filter.deviceid;


import java.util.List;

import net.realmproject.dcm.event.filter.NegationFilter;


/**
 * DeviceEvent filter which blocks events with certain device ids
 * 
 * @author NAS
 *
 */

public class DeviceIDBlacklistFilter extends NegationFilter {

    public DeviceIDBlacklistFilter(String id) {
        super(new DeviceIDWhitelistFilter(id));
    }

    public DeviceIDBlacklistFilter(List<String> ids) {
        super(new DeviceIDWhitelistFilter(ids));
    }

}
