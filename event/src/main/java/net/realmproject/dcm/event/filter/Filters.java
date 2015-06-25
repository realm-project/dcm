package net.realmproject.dcm.event.filter;


import java.util.List;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.filter.deviceeventtype.MessageFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.ValueChangedFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.ValueGetFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.ValueGetSetFilter;
import net.realmproject.dcm.event.filter.deviceeventtype.ValueSetFilter;


public class Filters {

    public static Predicate<DeviceEvent> sourceId(String id) {
        return new SourceIDFilter(id);
    }

    public static Predicate<DeviceEvent> targetId(String id) {
        return new TargetIDFilter(id);
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
    public static Predicate<DeviceEvent> getSetEvents() {
        return new ValueGetSetFilter();
    }

    public static Predicate<DeviceEvent> messageEvents() {
        return new MessageFilter();
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

    public static Predicate<DeviceEvent> all(List<Predicate<DeviceEvent>> filters) {
        Predicate<DeviceEvent> predicate = (deviceEvent) -> true;
        for (Predicate<DeviceEvent> filter : filters) {
            predicate = predicate.and(filter);
        }
        return predicate;
    }

    public static Predicate<DeviceEvent> any(List<Predicate<DeviceEvent>> filters) {
        Predicate<DeviceEvent> predicate = (deviceEvent) -> false;
        for (Predicate<DeviceEvent> filter : filters) {
            predicate = predicate.or(filter);
        }
        return predicate;
    }

}
