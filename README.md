[![Build Status](https://travis-ci.org/cpollet/itinerants.svg?branch=master)](https://travis-ci.org/cpollet/itinerants)

# Build everything
```
$ mvn clean install
```

To build all docker images you can use
```
$ mvn clean install -Pbuild-docker-image
```

# Start the webapp
## Using docker-compose
Make sure you build the docker images and run
```
$ docker-compose up
```

You might want to load the content of neo4j/database.cypher in neo4j. The following can help:

```
$ docker exec -it itinerants_neo4j_1 bin/neo4j-shell
```

You can access the various services using the following URLs:

 * webapp: http://localhost:8000
 * webservice: http://localhost:8000/api

If you need to remove all generated images, you can run the `clean-docker.sh` script or
`mvn -Ddocker.removeAll docker:remove` if you only want to get rid of the latest images.

## Start in dev
```
$ devops/start.sh
$ mvn -f webservice/pom.xml clean install
$ mvn -f webservice/web/pom.xml spring-boot:run
```

You can access the various services using the following URLs:

 * webapp: http://localhost:3000
 * webservice: http://localhost:8080 or http://localhost:3000/api
 * neo4j: http://localhost:7474/
 * rabbitmq: http://localhost:8000 (guest:guest)

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
$ mkdir -p `go env GOPATH`/src/cpollet.net 
$ ln -s /path/to/itinerants/cli/src/cpollet.net/itinerants `go env GOPATH`/src/cpollet.net/`
$ go get golang.org/x/crypto/ssh
$ go install cpollet.net/itinerants
```

If `` `go env GOPATH`/bin `` is in your `PATH`, you can run `itinerants`. Otherwise, you can use `` `go env GOPATH`/bin/itinerants ``.
