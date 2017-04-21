# Start in dev
```
$ neo4j/start.sh
$ mvn -f webservice/pom.xml clean install
$ mvn -f webservice/web/pom.xml spring-boot:run
$ (cd webapp && npm install && npm start)
```

You might want to load the content of neo4j/databays.cypher in neo4j...

 * webapp: http://localhost:3000
 * webservice: http://localhost:8080 or http://localhost:3000/api
 * neo4j: http://localhost:7474/

# Using docker-compose
```
$ mvn clean install -Pbuild-docker-image
$ docker-compose up
```

You might want to load the content of neo4j/databays.cypher in neo4j...

 * webapp: http://localhost:8000
 * webservice: http://localhost:8000/api
 * neo4j: http://localhost:7474/
