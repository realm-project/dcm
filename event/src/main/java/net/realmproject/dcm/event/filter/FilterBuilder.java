package net.realmproject.dcm.event.filter;


import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;
import net.realmproject.dcm.event.filter.filters.PayloadClassFilter;
import net.realmproject.dcm.event.filter.filters.SourceIDFilter;
import net.realmproject.dcm.event.filter.filters.TargetIDFilter;
import net.realmproject.dcm.event.filter.filters.ZoneFilter;
import net.realmproject.dcm.event.filter.filters.deviceeventtype.MessageFilter;
import net.realmproject.dcm.event.filter.filters.deviceeventtype.ValueChangedFilter;
import net.realmproject.dcm.event.filter.filters.deviceeventtype.ValueGetFilter;
import net.realmproject.dcm.event.filter.filters.deviceeventtype.ValueGetSetFilter;
import net.realmproject.dcm.event.filter.filters.deviceeventtype.ValueSetFilter;


public class FilterBuilder implements Predicate<DeviceEvent> {

    Predicate<DeviceEvent> filter = event -> true;
    BiFunction<Predicate<DeviceEvent>, Predicate<DeviceEvent>, Predicate<DeviceEvent>> reducer = (e1, e2) -> e1.and(e2);

    public static FilterBuilder start() {
        return new FilterBuilder();
    }

    @Override
    public boolean test(DeviceEvent t) {
        return filter.test(t);
    }

    public FilterBuilder eventGetSet() {
        merge(new ValueGetSetFilter());
        return this;
    }

    public FilterBuilder eventMessage() {
        merge(new MessageFilter());
        return this;
    }

    public FilterBuilder eventGet() {
        merge(new ValueGetFilter());
        return this;
    }

    public FilterBuilder eventSet() {
        merge(new ValueSetFilter());
        return this;
    }

    public FilterBuilder eventChange() {
        merge(new ValueChangedFilter());
        return this;
    }

    public FilterBuilder zone(String zone) {
        merge(new ZoneFilter(zone));
        return this;
    }

    public FilterBuilder source(String id) {
        merge(new SourceIDFilter(id));
        return this;
    }

    public FilterBuilder target(String id) {
        merge(new TargetIDFilter(id));
        return this;
    }

    public FilterBuilder payload(Class<?> cls) {
        merge(new PayloadClassFilter(cls));
        return this;
    }

    public FilterBuilder setMergeFunction(
            BiFunction<Predicate<DeviceEvent>, Predicate<DeviceEvent>, Predicate<DeviceEvent>> merge) {
        reducer = merge;
        return this;
    }

    public FilterBuilder and() {
        reducer = (e1, e2) -> e1.and(e2);
        return this;
    }

    public FilterBuilder or() {
        reducer = (e1, e2) -> e1.or(e2);
        return this;
    }

    private void merge(Predicate<DeviceEvent> other) {
        filter = reducer.apply(filter, other);
    }

    @SafeVarargs
    public static Predicate<DeviceEvent> all(Predicate<DeviceEvent>... filters) {
        return all(Arrays.asList(filters));
    }

    @SafeVarargs
    public static Predicate<DeviceEvent> any(Predicate<DeviceEvent>... filters) {
        return any(Arrays.asList(filters));
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
