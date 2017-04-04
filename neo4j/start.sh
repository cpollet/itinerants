#!/bin/bash

rm /Users/cpollet/.neo4j/known_hosts

docker run --rm \
    -p 7474:7474 -p 7687:7687 \
    neo4j:3.0