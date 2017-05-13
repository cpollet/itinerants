#!/usr/bin/env bash

rm ~/.neo4j/known_hosts

docker run -d --rm --name neo4j -p 7474:7474 -p 7687:7687 -v itinerants_neo4j-data:/data --env=NEO4J_AUTH=none neo4j:3.0
