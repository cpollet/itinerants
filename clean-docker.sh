#!/usr/bin/env bash

for i in `docker images --format "{{.ID}} {{.Repository}}" | grep cpollet/net.cpollet.itinerants- | cut -d' ' -f1`; do
    docker rmi -f $i
done
