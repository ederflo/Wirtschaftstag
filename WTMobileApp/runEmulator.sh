#!/bin/bash

EmulatorPath='D:/AndroidSDK/emulator'
DeviceName='Nexus_4_API_29'

echo
echo "Starting emulator"
	$EmulatorPath/emulator -avd $DeviceName
if [ $? -ge 1 ]; then 
	exit
fi