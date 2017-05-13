#!/usr/bin/env bash

echo Start Neo4j...
devops/neo4j/start.sh

echo Start RabbitMQ...
devops/rabbitmq/start.sh

echo Start webapp...
devops/webapp/start.sh
