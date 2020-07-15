# Relay Node

Node with API exposed for controlling relays.

## Workflow
* Node will enroll with the hub upon startup as a relay type module
* Frontend will query the backend for module address
* Frontend will send GET /relays
* Node will return list of relays that belong to that model

## Configurable for 1->n relays
* Use config.properties file to list relay title and pin #
* https://crunchify.com/java-properties-file-how-to-read-config-properties-values-in-java/
* https://stackoverflow.com/questions/1318347/how-to-use-java-property-files
* https://stackoverflow.com/questions/28326842/java-iterate-through-properties-file


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
java -jar ./target/relay-node-0.0.1.jar
```

The server will now be running on port 8080 with an included mock dataset.
See below for running command line manual testing.

## Manual testing

Testing the server via command line

### REST test examples


## Deployment

Add additional notes about how to deploy this on a live system

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management
