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
