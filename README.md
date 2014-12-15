Device Control Module
===

The Device Control Module is an event handling system designed to facilitate interaction with physical devices. Some of it's features include:

 * Low-level API for getting/setting device values
 * High-level API for issuing commands to devices
 * Hub-and-spoke event bus for loose coupling
 * Designed with web services in mind
 * Support for event systems spanning many machines with message queuing
 * Built-in event filtering and device isolation

Basic Layout
---

![Basic Layout](documentation/images/layout-basic.png)

The simplest layout uses a single event bus on one machine, with a java device controller operating a device. A writer will generate events, the bus will relay them to the device, the device will publish it's new state, and the reader will read and store that new state.

A few things to note:

 * Readers cache the state of a device to prevent a high volume of queries from negatively impacting the performance of a device.
 * All events contain a Device ID. This is the id of the device sending or receiving the event.
 * A Framework exist to make creating a device object simple, but anything which subscribes to an event bus can be used to control a device

