#!/bin/bash

cd /home/ubuntu/bukkit-server

echo "***"
echo "Killing current server instance"
echo "***"

./kill.sh

echo "***"
echo "Getting latest PaperMC version"
echo "***"

node ./update-jar.js

echo "***"
echo "Backing up world"
echo "***"

# World backup (only overworld to save space)
now=$(date)
git add world
git commit -m "World backup ($now)"
git push

echo "***"
echo "Starting server"
echo "***"

java -Xms5120M -Xmx7680M -jar bukkit.jar -o true
