package net.realmproject.dcm.parcel.node.filter;


import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import net.realmproject.dcm.parcel.Parcel;

public class FilterBuilder implements Predicate<Parcel<?>> {

    Predicate<Parcel<?>> filter = parcel -> true;
    BiFunction<Predicate<Parcel<?>>, Predicate<Parcel<?>>, Predicate<Parcel<?>>> reducer = (p1, p2) -> p1.and(p2);

    public static FilterBuilder start() {
        return new FilterBuilder();
    }

    @Override
    public boolean test(Parcel<?> t) {
        return filter.test(t);
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
            BiFunction<Predicate<Parcel<?>>, Predicate<Parcel<?>>, Predicate<Parcel<?>>> merge) {
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

    private void merge(Predicate<Parcel<?>> other) {
        filter = reducer.apply(filter, other);
    }

    @SafeVarargs
    public static Predicate<Parcel<?>> all(Predicate<Parcel<?>>... filters) {
        return all(Arrays.asList(filters));
    }

    @SafeVarargs
    public static Predicate<Parcel<?>> any(Predicate<Parcel<?>>... filters) {
        return any(Arrays.asList(filters));
    }

    public static Predicate<Parcel<?>> all(List<Predicate<Parcel<?>>> filters) {
        Predicate<Parcel<?>> predicate = (parcel) -> true;
        for (Predicate<Parcel<?>> filter : filters) {
            predicate = predicate.and(filter);
        }
        return predicate;
    }

    public static Predicate<Parcel<?>> any(List<Predicate<Parcel<?>>> filters) {
        Predicate<Parcel<?>> predicate = (parcel) -> false;
        for (Predicate<Parcel<?>> filter : filters) {
            predicate = predicate.or(filter);
        }
        return predicate;
    }

}
