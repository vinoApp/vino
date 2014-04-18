![alt tag](https://cdn1.iconfinder.com/data/icons/nuvola2/128x128/mimetypes/exec_wine.png) vino
====

h3. Abstract

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

h3. Bootstrap

Vino is bundled with a Mongo dump containing a subset of French WineDomains (Bordeaux, ...), and a subset of Wine AOC & regions.
This dump could be found in the folder vino-srv/db/dump and restored liked this :

```
mongorestore --db vino db/dump/vino
```

This command will populate your vino local database.

