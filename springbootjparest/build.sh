#!/bin/bash

if [ -d ./target ]; then
	rm -r ./target
fi

echo
echo "Compiling spring-boot webservice"
	mvn package
if [ $? -ge 1 ]; then 
	exit
fi


echo
echo "Building docker container..."
	docker build -t springbootappjpa .
if [ $? -ge 1 ]; then 
	exit
fi


echo
echo "Starting the docker containers..."
	docker-compose up
if [ $? -ge 1 ]; then 
	exit
fi