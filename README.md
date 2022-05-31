# Gov data dashboard **prototype**

Hello my friend,

warning! 
This is a prototype!

So yes - I did no TDD and I am not sorry as this prototype is meant to learn more about what I want to build
and thus I will do quiet a lot mistakes.

Please look at this code with the right amount of expectations :)

## About the service

In germany fedral ministries have to publish their data by law, but few are doing it.
This service provides a dashboard where one can see the amount of published data sets per federal ministry.

## How to start

As I am using gradle, you can use the gradle wrapper to start the application via the command line interface:

    ./gradlew bootRun

Afterwards you can...

(a) visit the dashboard

http://localhost:8080/

(b) look at the dashboard data as json

http://localhost:8080/api/v1/federal-ministry-dashboard

or (c) check out the list of all govDataResponse from gov data (not only felder ministries)

http://localhost:8080/api/v1/govDataResponse