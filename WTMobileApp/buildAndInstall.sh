#!/bin/bash

echo
echo "Building and installing app"
	./gradlew installDebug
if [ $? -ge 1 ]; then 
	exit
fi