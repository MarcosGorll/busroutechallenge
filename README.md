# Bus Route Challenge

## Problem

A new product is being developed that would allow larger transportation
vehicles, even up to the size of a bus, to offer services to customers who
are looking for inexpensive travel options.  Unlike a normal car service,
the busses only travel along specific routes.  When considering what travel
options a customer has, it must be known whether a bus route exists that will
connect the pickup location of the customer to the dropoff location.

The bus service providers submit a bus route file every week.  The number of
bus routes can be very large, so a service is needed that can quickly answer
if two locations (pickup and dropoff) are connected by a bus route.

## Task

You are provided a data file representing all the bus routes for this week.
The format of the file is as follows:

```
[number_of_routes]
[routeId1] [location1.1] [location1.2] ...
[routeId2] [location2.1] [location2.2] ...
...
```

Just to clarify, here are a few example lines:
```
3
1001 3 8 2
1002 2 3 5 7
1003 7 8 3 2
```

So in this file, bus route 1001 starts at location 3, next goes to location 8, and
finally to location 2.  Bus route 1002 starts at location 2, then goes to location
3, location 5, and finally location 7.  One should **NOT** assume that the bus goes
back to its first location.

Your task is to implement a micro-service that is able to answer whether there
is a **direct** connection between two given locations.  In this context, a
micro-service is a small stand-alone web-server that services a REST API.  It is
not customer facing, so a pretty user interface is not necessary.

## Constraints
 * Location IDs are 32 bit integers.
 * A requested pickup or dropoff location may not be in any bus route.  There
   are more transportation means than just busses.
 * A single location may not be in a single bus route more than once.
 * There will never be more than 100,000 bus routes.
 * The maximum number of locations is 1,000,000.
 * No bus route will be listed more than once.

## Command Line Input

The name of the file containing the bus routes should be accepted as the first
command line argument.  If this file ever changes, you server will be restarted,
so there is no need to watch for this event.

## REST API

Your micro-service should implement a REST-API supporting a single URL and only
GET requests.

Requests will be of the form `/api/direct?pickup_id=[integer]&dropoff_id=[integer]`.
For example, a request to a local server may be to
`http://localhost:8088/api/direct?pickup_id=5&dropoff_id=8`.

The response should be a single JSON Object of the form:
```
{
  "pickup_id": <integer>,
  "dropoff_id": <integer>,
  "has_direct_route": <boolean>
}
```

For example:
```
{ "pickup_id": 5, "dropoff_id": 8, "has_direct_route": false }
```

## Example Data

Bus Routes Data File:
```
3
1000 0 1 2 3 4
1001 3 1 6 5
1002 0 6 4
```

Query:
````
http://localhost:8088/api/direct?pickup_id=3&dropoff_id=6
```

Response:
```
{
    "pickup_id": 3,
    "dropoff_id": 6,
    "has_direct_route": true
}
```

## Implementation

Though this task is relatively small, keep your code neat, and write tests.

The technologies we frequently use at Careem are:
 1. *Programming Language*: Java 1.8
 2. *Build Tool*: Maven
 3. *Web Framework*: Spring Bootstrap
 4. *Unit Tests*: Mockito
 5. *Controller Tests*: Fair documentation can be found here: http://spring.io/guides/gs/testing-web/
 
If these technologies are new to you, this can be a good excuse to try out
something different.  If you want to try out something different, that is all
right too.