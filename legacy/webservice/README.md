# Build Docker image
```
$ docker build -t itinerants .
```

# Run docker image in a container
```
$ docker run -d -p 0.0.0.0:8000:8080 --name itinerants itinerants
```

# Watch the logs
```
$ docker logs -f {containerId}
```

# Gandi and Docker
## Ubuntu 14.04 LTS, raw Docker
Just follow the instructions at [Docker](https://docs.docker.com/engine/installation/linux/ubuntulinux/):
```
$ apt-get update
$ apt-get install apt-transport-https ca-certificates
$ apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D
$ echo "deb https://apt.dockerproject.org/repo ubuntu-trusty main" > /etc/apt/sources.list.d/docker.list
$ apt-get update
$ apt-get purge lxc-docker
$ apt-cache policy docker-engine
$ sudo apt-get install docker-engine
$ sudo service docker start
$ sudo docker run hello-world
```

## Debian 8, with Docker Cloud Agent
```
$ apt-get update
$ apt-get install -y curl htop
$ curl -Ls https://get.cloud.docker.com/ | sh -s {yourKey}
```