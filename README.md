# Build the webapp
## Using docker-compose
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

## Start in dev
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

# Build the CLI
## Using Maven
You don't need any local go installation, the plugin will take care of everything.
```
$ mvn -f cli/pom.xml clean install
$ cli/bin/itinerants
```

## Using system GO installation
Put source files in your `GOPATH` and then install and run the executable.
```
$ mkdir -p $GOPATH/src/cpollet.net && ln -s /path/to/itinerants/cli/src/cpollet.net/itinerants $GOPATH/src/cpollet.net/`
$ go get golang.org/x/crypto/ssh
$ go install cpollet.net/itinerants
```

Provided that `$GOPATH/bin` is in your `PATH`, you can run `itinerants`.

# Build everything
```
$ mvn clean install
```