# ![alt tag](https://cdn1.iconfinder.com/data/icons/nuvola2/128x128/mimetypes/exec_wine.png) vino

## Abstract

### Current versions
server-current-version = 1.0  
webui-current-version = 1.0  
mobile-current-version = 1.0  


### About the solution
Wine cellar manager (web + mobile clients).
This solution is functionnaly able to manage a wine cellar. On the one hand, the web client is able to expose data 
(stored bottles, all bottles, bottles sticks, statistics, ...), on the other hand, the mobile client is able to 
register/unregister a stored bottle reading the bar code on the bottle.


The solution is built on the following technical stack (excluding standard tools) :
- WebUI : AngularJS & Twitter Bootstrap
- Mobile : Android SDK
- Server (Java) : [restx.io](http://restx.io/) that exposes a set of REST WebServices.


### Data
Some crawlers are available on request and allow to populate the database with wine domains of the region of Bordeaux, Bourgogne...


