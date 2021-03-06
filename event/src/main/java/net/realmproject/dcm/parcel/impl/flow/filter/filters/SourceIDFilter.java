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


/**
 * Parcel filter which only accepts parcels with certain source ids
 * 
 * @author NAS
 *
 */

public class SourceIDFilter implements Predicate<Parcel<?>> {

    List<String> ids;

    public SourceIDFilter(String id) {
        ids = new ArrayList<>();
        ids.add(id);
    }

    public SourceIDFilter(List<String> ids) {
        this.ids = new ArrayList<>(ids);
    }

    @Override
    public boolean test(Parcel<?> e) {
        return ids.contains(e.getSourceId());
    }

}
