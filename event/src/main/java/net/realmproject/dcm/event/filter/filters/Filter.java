package net.realmproject.dcm.event.filter.filters;


import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;


public interface Filter extends Predicate<DeviceEvent> {

}
