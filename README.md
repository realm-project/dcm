Device Control Module
===

The Device Control Module is an event handling system designed to facilitate interaction with physical devices. Some of it's features include:

 * Bus-based design for loose coupling
 * Support for bus topology spanning many machines with message queuing 
 * High-level API for issuing commands to devices
 * Low-level API for getting/setting device values
 * Designed with web services in mind
 * Built-in event filtering and device isolation


Event Bus Topology
---

### Basic Layout


The simplest layout uses a single event bus on one machine, with a java Device object operating a physical device. An Accessor will will generate events, the Bus will relay them to the device, the device will publish it's new state, and the accessor will read and store that new state.

![Basic Layout](documentation/images/layout-basic.png)

A few things to note:

 * Accessors cache the state of a device to prevent a high volume of queries from negatively impacting the performance of a device.
 * All events contain a Device ID. This is the id of the Device sending or receiving the event.
 * A framework exists to make creating a Device object simple, but anything which subscribes to a Bus can be made to control a device.
 * More than one bus can be used. Events can be selectively forwarded to other busses. Be careful not to create cycles.

### Tiered Layout


To isolate your devices from the variable loads put on web servers, you can separate your system into a web tier and a device teir. These are tied together with message queueing. The Device Control Module contains built-in support for ActiveMQ, but you can also create your own adapters.

![Tiered Layout](documentation/images/layout-tiered.png)

A few things to note:

 * MQ is accessed through Senders and Receivers (not shown) which publish and subscribe to event busses like any other component.
 * The message queuing server can be on a dedicted server, or reside on either of the machines shown.


### Multi-device Layout

You may have several different devices which are logically related, but which reside on different machines. To accommodate this, the Device Control Module supports the notion of Zones. A zone is a property of an event bus, and more than one bus may share a zone name. 

![Network Layout](documentation/images/layout-network.png)

When events are published, they contain a null zone field. When an event is first published, a bus will set the event's zone to its own, after which, other busses the event passes through will not. This allows you to filter events coming in and out of any given bus by zone, so that zone-specific events can be forwarded to related busses.

A few things to note:

 * Components can communicate with each other through message passing using this method. They are not limited to passing status updates to an Accessor.
 * Events can be marked private, which instructs the event bus system not to forward the events out of their zone.

Command Driven Devices (High-Level API)
---

### Commands

The CommandDevice class (or the Commands interface if subclassing is a problem) provides an annotation-driven approach to designing device controllers which respond to commands.

Subclassing CommandDevice allows your device to respond to Commands by annotating public methods with the @CommandMethod annotation. If no name is given with the annotation, the name of the Command will be the name of the method.

```java
@CommandMethod("foo")
public void do_foo(@Arg("bar") T bar, @Arg("baz") S baz) {
	// ...
}
```

Commands can be issued with a DeviceWriter, or by setting a Command object as the payload in a DeviceEvent manually.

A few things to note:
 * Method arguments should be annotated with @Arg(name). Command objects have an arguments Map with String keys. These annotations allows named arguments from this mapping.
 * @Arg annotations which do not specify a name will default to "value".
 * Zero-argument functions do not need any @Arg annotations.
 * Single-argument functions which to not have an @Arg annotation will attempt to convert the entire argument map into an instance of the argument class. Use with caution.

When using the Commands interface instead of the CommandDevice class, the Commands interface must be initialized by calling `initCommands()`

### Connection

The Connection interface is meant for device controllers which must maintain a persistent connection of some sort to the controlled device. 

It requires the implementation of the following methods:

```java
void connect();
void onConnect();
void onDisconnect(Exception exception);
```

The `connect` method should create a new connection to the device. If this connection is terminated, the `disconnected` method should be called, which will in turn call `onDisconnect`, and then attempt to create a new connection.

`onConnect` and `onDisconnect` notify the subclass when a (dis)connection occurs.


The Connection interface must be initialized by calling `initConnection()`

### Heartbeat

If you require polling in order to determine if your connection is still alive, the Heartbeat interface will handle some of the internals automatically. 

It builds on ConnectedCommandDevice, and requires the implementation of the following methods:

```java
boolean isHeartbeatStale();
```

Where `isHeartbeatStale` should test if the connection is still alive. If it is not, Heartbeat will begin the process of creating a new connection.

The Heartbeat interface must be initialized by calling `initHeartbeat()`

Simple Get/Set Devices (Low-Level API)
---

### ValueDevice

If you want to create your own method of interacting with device controllers, you can use the low-level API. The ValueDevice class (or Values interface if subclassing is an issue) provides a base implementation of a device with an ID. It automatically subscribes to get/set events for it's own ID. Subclassing ValueDevice allows you to publish and consume Java objects in an unstructured way. 

It requires the implementation of the following methods:

```java
Object getValue();
void setValue(Object val);
```

### Starting From Scratch

If you want to work at an even lower level, producing and consuming DeviceEvent objects directly, you can create your own class which interacts with a DeviceEventBus directly. A simple example of such a class is shown below.

```java

public class CustomDevice {
	
	String id;
	DeviceEventBus bus;

	public CustomDevice(String id, DeviceEventBus bus) {
		this.id = id;
		this.bus = bus;
		bus.subscribe(this::onEvent, new DeviceIDFilter(id).and(new BackendFilter()));
	}

	private void onEvent(DeviceEvent e) {
		// process events here
	};

}

```

