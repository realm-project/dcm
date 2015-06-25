package net.realmproject.dcm.event.filter;


import java.util.function.Predicate;

import net.realmproject.dcm.event.DeviceEvent;


public interface Filter extends Predicate<DeviceEvent> {

}
