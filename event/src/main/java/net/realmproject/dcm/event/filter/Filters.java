package net.realmproject.dcm.event.filter;


import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.filter.deviceeventtype.BackendFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.FrontendFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.PingFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.PongFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.ValueChangedFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.ValueGetFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.ValueSetFilter;


public class Filters {

    public static Predicate<DeviceEvent> id(String id) {
        return new DeviceIDFilter(id);
    }

    // /////////////////////////
    // Zone
    // /////////////////////////
    public static Predicate<DeviceEvent> zone(String zone) {
        return new ZoneFilter(zone);
    }

    // /////////////////////////
    // Device Event Type
    // /////////////////////////
    public static Predicate<DeviceEvent> frontendEvents() {
        return new FrontendFilter();
    }

    public static Predicate<DeviceEvent> backendEvents() {
        return new BackendFilter();
    }

    public static Predicate<DeviceEvent> pingEvents() {
        return new PingFilter();
    }

    public static Predicate<DeviceEvent> pongEvents() {
        return new PongFilter();
    }

    public static Predicate<DeviceEvent> getEvents() {
        return new ValueGetFilter();
    }

    public static Predicate<DeviceEvent> setEvents() {
        return new ValueSetFilter();
    }

    public static Predicate<DeviceEvent> changedEvents() {
        return new ValueChangedFilter();
    }

}
