# Using docker-compose
```
$ mvn clean install -Pbuild-docker-image
$ docker-compose up
```

You might want to load the content of neo4j/database.cypher in neo4j. The following can help:

```
$ docker exec -it itinerants_neo4j_1 bin/neo4j-shell
```

You can access the various services usign the following URLs:

 * webapp: http://localhost:8000
 * webservice: http://localhost:8000/api
 * neo4j: http://localhost:7474/

If you need to remove all generated images, you can run the `clean-docker.sh` script or
`mvn -Ddocker.removeAll docker:remove` if you only want to get rid of the latest images.

# Start in dev
```
$ neo4j/start.sh
$ mvn -f webservice/pom.xml clean install
$ mvn -f webservice/web/pom.xml spring-boot:run
$ (cd webapp && npm install && npm start)
```

You can access the various services usign the following URLs:

 * webapp: http://localhost:3000
 * webservice: http://localhost:8080 or http://localhost:3000/api
 * neo4j: http://localhost:7474/

