# Relay Node

Node with API exposed for controlling relays.

## TODO
* Expose a /health GET option for healthchecks with logical checks

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
java -jar -Drelays=Compressor,Purge-Valve -Dpins=0,2 -Dtesting=true ./target/relay-node-0.0.1.jar
```

The server will now be running on port 8080 with an included mock dataset.
See below for running command line manual testing.

## Manual testing

Testing the server via command line

### REST test examples

* PUT /relays/{id}
    * curl -X PUT localhost:8083/relays/1 -H 'Content-type:application/json' -d '{"id": "1", "title": "Compressor", "state": "true"}'


## Deployment

Add additional notes about how to deploy this on a live system

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management
