TEST Callback.

Project used to register callback URL which has time notifications sent.

Developed using:

* Spring boot
* Java 11

## Starting the app:

Jar can be found inside the target folder after running a maven build. ie mvn clean install

java -jar test-ijog-XXXX.jar

The application should be available on localhost port 8091  (set in properties)

## Usage, Endpoint:

Application endpoint is this single one:

http://localhost:8091/clock/notification

Payload takes a string url and a frequency specified in seconds ONLY, minimum 5 seconds, and maximum 14400 seconds (4 hours)

1. POST - Create

Endpoint: http://localhost:8091/clock/notification

Content type : Json

Payload Example:  {"url":"http://somewhere/notify","frequency":10}

2. PUT - Update

Endpoint: http://localhost:8091/clock/notification

Content type : Json

Payload Example:  {"url":"http://somewhere/notify","frequency":20}


3. DELETE - Remove  (this requires a 'url' request paramater added to the endpoint as below to work)

Example:

http://localhost:8091/clock/notification?url=http://somewhere/notify

Note: Payload for PUT and UDPATE:

{"url":"http://somewhere/notify","frequency":10}

## Basic testing 

There is a very very basic controller TestCallbackController.java available on the application, specifically at
http://localhost:8091/clock/notification/test (within this app). This can be used to check callbacks, I added this only for this TEST project and is never meant to be done 
like this in real production code.

Need to set via a post call as follows, monitor the logs or console:

{"url":"http://localhost:8091/clock/notification/test","frequency":10}

Now monitor the logs..

## Framework Info:

Spring boot can handle simultaneously requests, having said that it all depends on tomcat under the hood, which has a limitation, it can only handle 200 simultaneous requests only. If I want more than this was required, I would need to look at increasing the thread count or using something more performant 
like spring webflux which a lot quicker. 

Another option of really determining the throughput would be to set up jmeter and see how the application performs under load. 
I am using Concurrent hashmap as a store, which is more suited to concurrent access due its finer grained locking should also help.
Again im assuming this should be enough, and without knowing a benchmark, and testing more - i have gone as far as i can.

## Areas left to work on/explore:

* Swagger: This would give excellent information ont he API and would be a nice to have,
* Tests: many classes dont have enough tests.
* URL Validation: Started this but this needed further looking into.
* Introduction of Webflux in spring to help speed it up.
* Look into other frameworks like vert.x which follows the single threaded reactor model which is even more performant than spring. (this would require more than a couple of hours)


