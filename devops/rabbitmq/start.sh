#!/usr/bin/env bash

docker run -d --rm --name rabbitmq -p 5672:5672  -p 9000:15672 rabbitmq:3-management
