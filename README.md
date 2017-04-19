# Raw
```
$ neo4j/start.sh
$ mvn -f webservice/pom.xml clean install
$ mvn -f webservice/web/pom.xml spring-boot:run
$ (cd webapp && npm install && npm start)
```

You might want to load the content of neo4j/databays.cypher.

 * webapp: http://localhost:3000
 * webservice: http://localhost:8080 or http://localhost:3000/api
 * neo4j: http://localhost:7474/

# Using docker
these are only notes, it's still in progress...
```
$ neo4j/start.sh
$ mvn -f webservice/pom.xml clean package
$ mvn -f webservice/web/pom.xml docker:build
$ docker run -it --rm -p 8080:8080 cpollet/net.cpollet.itinerants-webservice-web
```
