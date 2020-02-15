#!/bin/bash

cd /home/ubuntu/bukkit-server

# World backup (only overworld to save space)
now=$(date)
git add world
git commit -m "World backup ($now)"
git push

# Check for latest update
echo "-----\nGetting latest server version\n-----"
java -jar BuildTools.jar --rev latest >> ./log

# Remove the old bukkit.jar and replace with new one
echo "-----\nReplacing bukkit.jar file\n-----"
node ./update-bukkit.js >> ./log

# Start the server
echo "-----\nStarting the server\n-----"
java -Xmx3G -jar bukkit.jar -o true >> ./log
