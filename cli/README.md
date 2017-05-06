# Compile
You have two choices... Either use the mvn-golang-wrapper plugin or standard go installation.

## Maven plugin
```
$ mvn clean install
$ bin/itinerants
```

## Standard
```
$ export GOPATH=$GOPATH:`pwd`
$ go install net/cpollet/itinerants/cli/cmd/itinerants
$ bin/itinerants
```
