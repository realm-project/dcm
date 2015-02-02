package net.realmproject.dcm.event.filter;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.filter.composite.BooleanAndFilter;
import net.realmproject.dcm.event.filter.composite.BooleanOrFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.BackendFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.FrontendFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.PingFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.PongFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.ValueChangedFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.ValueGetFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.ValueSetFilter;
import net.realmproject.dcm.event.filter.deviceid.DeviceIDWhitelistFilter;
import net.realmproject.dcm.event.filter.region.RegionWhitelistFilter;


public class Filters implements Iterable<Predicate<DeviceEvent>> {

    private List<Predicate<DeviceEvent>> filters = new ArrayList<>();

    public static Filters filter() {
        return new Filters();
    }

    // /////////////////////////
    // Device ID
    // /////////////////////////
    public Filters id(String id) {
        filters.add(new DeviceIDWhitelistFilter(id));
        return this;
    }

    // /////////////////////////
    // Region
    // /////////////////////////
    public Filters region(String region) {
        filters.add(new RegionWhitelistFilter(region));
        return this;
    }

    // /////////////////////////
    // Device Event Type
    // /////////////////////////
    public Filters frontendEvents() {
        filters.add(new FrontendFilter());
        return this;
    }

    public Filters backendEvents() {
        filters.add(new BackendFilter());
        return this;
    }

    public Filters pingEvents() {
        filters.add(new PingFilter());
        return this;
    }

    public Filters pongEvents() {
        filters.add(new PongFilter());
        return this;
    }

    public Filters getEvents() {
        filters.add(new ValueGetFilter());
        return this;
    }

    public Filters setEvents() {
        filters.add(new ValueSetFilter());
        return this;
    }

    public Filters changedEvents() {
        filters.add(new ValueChangedFilter());
        return this;
    }

    // /////////////////////////
    // Collecting
    // /////////////////////////

    public Predicate<DeviceEvent> booleanAnd() {
        return new BooleanAndFilter(filters);
    }

    public Predicate<DeviceEvent> booleanOr() {
        return new BooleanOrFilter(filters);
    }

    @Override
    public Iterator<Predicate<DeviceEvent>> iterator() {
        return filters.iterator();
    }

}
