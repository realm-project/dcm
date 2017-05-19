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

package net.realmproject.dcm.parcel.impl.flow.filter.filters;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.flow.hub.ParcelHub;


/**
 * Parcel filter which only allows parcels from a certain zone. See
 * {@link ParcelHub}
 * 
 * @author NAS
 *
 */

public class ZoneFilter implements Predicate<Parcel<?>> {

    private List<String> whitelist;

    public ZoneFilter(String zone) {
        whitelist = new ArrayList<>();
        whitelist.add(zone);
    }

    public ZoneFilter(List<String> zones) {
        whitelist = new ArrayList<>(zones);
    }

    @Override
    public boolean test(Parcel<?> e) {
        return whitelist.contains(e.getZone());
    }
}
