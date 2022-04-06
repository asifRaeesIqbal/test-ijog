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

Application endpoint would be:

http://localhost:8091/clock/notification

Payload takes a string url and a frequency specified in seconds ONLY, minimum 5 seconds, and maximum 14400 seconds (4 hours)

POST - Create

Content type : Json, Payload Example:

{"url":"http://somewhere/notify","frequency":10}

PUT - Update

Content type : Json, Payload Example:

{"url":"http://somewhere/notify","frequency":10}


DELETE - Remove  

example:

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

Currently Spring boot can handle simultaneously requests, however tomcat under the hood has a limitation, it can only handle 200 simultaneous requests.
If I want more than this, I would need to look at performance upping the thread count or and as such something 
like spring webflux which a lot quicker. 

Another option of really determining the throughput would be to set up jmeter and see how the application performs under load. 
I am using Concurrent hashmap as a store, which is more suited to concurrent access due its finer grained locking should also help.
Again im assuming this should be enough, and without knowing a benchmark, and testing more.

## Areas left to work on:

* Swagger: This would give excellent information ont he API and would be a nice to have,
* Tests: many classes dont have enough tests.
* URL Validation: Started this but this needed further looking into.
* Introduction of Webflux in spring to help speed it up.
* Look into other frameworks like vert.x which follows the single threaded reactor model which is even more performant than spring. (this would require more thana couple of hours)


# Original Info

# Take Home Test (Back-End)

# Guide

This is a Java coding exercise.

You should take **no more than a few hours** to complete it. If you take longer, you are probably going further than you need to! Please don’t turn this into a huge project.

We know it’s hard to fit these things in around a full-time job, so you have **one week** to submit your solution. If you need longer, please get in touch.

To submit your solution, push the code to GitHub and send us the link. It can be public, just please don't mention Goji in the repository name, README or anything else that will allow other people to Google your solution.

We will discuss your solution further with you via a Zoom call if you pass this stage.

**You won't be able to cover everything in just a few hours, so keep it simple, stick to conventions, and think about what you didn't have time for. You are encouraged to use in-code comments to indicate areas you think are incomplete or in need of improvement.**

# Functional Requirements

For this exercise, you must create a small, simple Java microservice which acts as a subscription-based clock.

It should allow clients to register a callback URL at any time and then receive notifications of the time of day at regular intervals as a webhook until they unregister the URL.

It must provide three endpoints, adhering to RESTful conventions. These should allow the client to:

- **Register:** provides (a) a URL to which the time should be sent and (b) the frequency at which callbacks should be sent. Should error if the client URL is already registered for callbacks. Once called, the callbacks should begin.
- **Deregister:** provides a URL previously registered.  Should error if the client URL is not registered for callbacks. Once called, callbacks to that URL should stop.
- Change the frequency of callbacks for a specific URL, to any time between 5 seconds and 4 hours.

The application should continuously POST the time to all registered URLs at the frequency requested.

All endpoints and the webhooks should use JSON for the message body, if there is one.

# Non-functional requirements

All solutions should:

- Use either Maven or Gradle.
    - Run a small number of suitable automated tests as part of the build.
    - Produce a standalone, runnable JAR.
- Ignore security issues such as authentication or SSL.
- Store state in memory only (don’t use a database). Assume that there will only be one instance of the application. Don’t worry about running out of memory.
- Handle lots of concurrent requests and lots of active webhooks without risk of data loss or crashing.

Feel free to use application frameworks such as Spring, Dropwizard or Micronaut, or no framework at all, as you see fit**.** Use whatever you are most comfortable with. **However, do not use Spring Data** or similar layers which auto-generate data store code, or in-memory databases such as H2. Please implement your in-memory data store yourself.

From senior candidates, we are looking for a clear understanding of **concurrency** and **high volume** as well as excellent code structure.

You are free to interpret the rest of the brief as you see fit.