geospatial-itinerary-intelligence
=================================
Geospatial Itinerary Intelligence (GII) is a location enabled mobile based application which assists tourists 
and travelers to make travelling plans and helps them to find nearest places around. To plan an itinerary using 
the system, user can specify intended cities he is about to travel. Based on the input provided by the user, 
the system will return various related itineraries consisting of cities specified with different travel metrics 
like cost of travel, distance of travel, popularity of travel. After planning the itinerary, user can travel 
in the itinerary using the map provided in the application, and can find his current location using 
Global Positioning System (GPS) services integrated in the application and eventually log different 
travel information.

There are three projects

1> Client Application
This is an android application build with Android Java. It uses basic android features:
- Android UI
- Google Places API
- Google Maps API

2> Server Application
This is a server developed with C# Web API. It hosts web services in order to provide different services 
to Android client. It interacts with clients (mobile app) through web services request and response in 
JSON (JavaScript  Object Notation) format. It uses Model View Controller (MVC) architecture to implement 
client server interaction.

3> Database Creation Script
This is a sql script that creates a database in Microsoft SQL server.
