# Relay Node

Node with API exposed for controlling relays.

## TODO

* Modify relay class for timer functionality (See below)
    * Changing storage would write data to file
* Modify timer class for sunrise/sunset capability
    * https://stackoverflow.com/questions/4935960/calculating-sunrise-and-sunset-with-java
* Add toggle relay functionality
    * Toggle-on time
* Create sensor <-> relay relationship
    * Many to Many
    * Relationships property?
* Add initial logic for door sensors
* Expose a /health GET option for healthchecks with logical checks

## Timer Functionality (To Add)
How do we utilize the base relay node to handle a schedule
What we currently have:
* Relay functionality with manual control (on/off) of state (true/false)
* API exposed for controlling state

What we need:
* A loop in the main function that executes a schedule check (utilize the relayDAO)
    * Is current time >= a trigger time
    * Check current state against on/off times
        * 
* A variable that identifies a relay as either a static (manual) or timer (automatic)
    * RelayDAO would check for this first before attempting some schedule logic
* A schedule variable to can be expanded upon (String with delimiters)
* A way to store the schedule
    * If the timer property is true, grab last schedule from directory
    * Add file operations to the RelayDAO.init() function
    * What private storage does the relay object have for schedule checking?

## Sensor Functionality
* We do not execute actions against sensors, rather the sensors execute actions.
* Would only need a GET / and GET /{id} API endpoints exposed
* Listeners - https://pi4j.com/1.2/example/listener.html
* Door Sensors
    * Time-of-execution boolean state
    * Would send a POST /Notification to the hub on trigger
    * Would not need a listener
* PIR Sensors
    * Maintain a lastSeen state
    * Would send a POST /Notification to the hub on trigger
    * Could be tied to a target object -> turn on relay
    * Would need a listener
* DS18b20 Temp sensor


## Workflow
* Node will enroll with the hub upon startup as a relay type module
* Frontend will query the backend for module address
* Frontend will send GET /relayModule to the hub
* The hub will query GET /relays/ and return the data to the frontend
* The frontend will create relay objects and assign to relaymodule objects with a dynamic ID (relaymodule ID + relay id)

To activate a relay
* The Frontend will execute some action and send a PATCH/PUT to the hub backend
* The hub backend will proxy that request to PUT /relays/ with the changed payload
* The relay module will process the relay states and activate/deactivate accordingly (based on new state attribute)
* Note: We could also utilize the PUT /relays/{id} by removing the module ID from the relay ID during proxy


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Necessary items to compile and run the server

```
JDK 1.8 or later
Maven 3.2+
Docker (when containerizing)
```

### Installing

A step by step series of examples that tell you how to get a development env running

Compile

```
mvn compile
```

Package

```
mvn package
```

Run

```
java -jar -Drelays=Compressor,Purge-Valve -Dpins=0,2 -Dtesting=true -Dtypes=timer,static ./target/automation-node-0.0.1.jar --server.port=8083
```

The server will now be running on port 8080 with an included mock dataset.
See below for running command line manual testing.

## Manual testing

Testing the server via command line

### REST test examples

* PUT /relays/{id}
    * curl -X PUT localhost:8083/relays/1 -H 'Content-type:application/json' -d '{"id": "1", "state": "true"}'


## Deployment

Add additional notes about how to deploy this on a live system

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management
