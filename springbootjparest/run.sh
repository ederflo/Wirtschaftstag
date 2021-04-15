#!/bin/bash

echo "Starting docker container"
	docker-compose up
if [ $? -ge 1 ]; then 
	exit
fi