[![Build Status](https://travis-ci.org/AshleyByeUK/echo-server.svg?branch=master)](https://travis-ci.org/AshleyByeUK/echo-server)
[![codecov](https://codecov.io/gh/AshleyByeUK/echo-server/branch/master/graph/badge.svg)](https://codecov.io/gh/AshleyByeUK/echo-server)
[![Maintainability](https://api.codeclimate.com/v1/badges/ad7ff723d172b8b0eb36/maintainability)](https://codeclimate.com/github/AshleyByeUK/echo-server/maintainability)

# echo-server

An echo server implementation in java.

## Installing

Clone this repository:

```
git clone git@github.com:AshleyByeUK/echo-server.git
```

Make a client and server directory and compile the source code for both the `EchoClient` and `EchoServer`:

```
cd echo-server
mkdir server client
javac -d server -cp src/main/java/ src/main/java/uk/ashleybye/echo/server/EchoServer.java
javac -d client -cp src/main/java/ src/main/java/uk/ashleybye/echo/client/EchoClient.java
```

## Running

The client and server need to be run in separate terminal windows. The client depends on the server, which must be
running prior to starting the client.

### Server

In a terminal window, run:

```
java -cp server uk.ashleybye.echo.server.EchoServer
```

### Client

In a separate terminal window, run:

```
java -cp client uk.ashleybye.echo.client.EchoClient
```

### Usage

In the client window, type some words and then press `<ENTER>`. The server will echo the words back to the client,
which displays the words on the screen. e.g.:

```
Hello, world!
Hello, world!
```

To stop the server and client, type `<ctrl+c>` in the client. Alternatively, stop the server with `<ctrl+c>` and
then stop the client with `<ctrl+c>`.

### Alternative usage

It is possible to run the server (see above) alone and use the `netcat` utility to interact with it:

```
nc 127.0.0.1 9999
Hello, world!
Hello, world!
```

Use `<ctrl+d>` to exit `netcat`.

Similarly, `socat` can be used to create an echo serve with which the client can interact:

```
socat TCP4-LISTEN:9999,fork EXEC:cat
```

Then launch the client, as above. Use `<ctrl+c>` to quit the client and again to quit the server.

## Testing

Both the client and server tests can be run with Gradle:

```
./gradlew test
```

Test coverage reports can be generated with Gradle:

```
./gradlew test jacocoTestReport
```

The generated reports can be viewed in the browser:

```
open build/reports/jacoco/test/html/index.html
```
